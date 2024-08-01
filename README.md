# Простой пример manual DI для многомодульного проекта

Большинство библиотек которые используются в Android разработке, такие как Dagger, Hilt или Koin основаны на принципе "контейнера с фабриками":

```
// фабрика для создания объектов
interface Factory<T> {
    fun create(): T
}

// аналогия в Dagger - компонент, аналогия в Koin - модуль
class Container {
    /*
    в качестве ключей может быть что угодно, KClass, String и тд, 
    главное иметь возможность получить зависимость когда это нужно
    и при этом не страдать
    */
    val map = mutableMapOf<KClass<*>, Factory<*>>() 
}
```

Несколько преимуществ такого решения:

1) Простота - ничего лишнего, простая коллекция с фабриками
2) Масштабируемость - компоненты можно объединить в один общий граф и использовать во многомодульных проектах
3) Удобство и расширяемость - компоненты можно положить куда угодно, во Fragment, в Activity, в Application и тд, всё зависит от вашей задачи

Я решил сделать что-то похожее для лучшего понимания как всё устроено под капотом и написал вот такое простенькое решение:

```
interface Factory<T> {
    fun create() : T
}

// я не стал создавать отдельный класс для контейнера, а сразу положил всё в object
object DI {

    // тут хранятся все фабрики, которые создают объекты когда они нужны
    val map: MutableMap<KClass<*>, Factory<*>> = mutableMapOf()

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T> instance(): T {
        /*
        получаем объект, в качестве ключа выступает KClass для класса создаваемого объекта,
        поэтому такое решение позволяет положить только один экземлпяр одного и того же класса
        */
        return map[T::class]?.create() as T
    }

    inline fun <reified T : Any> factory(crossinline dependencyProducer: () -> T) {
        // вариант фабрики, которая при каждом вызове create() создаёт новый объект
        map[T::class] = object : Factory<T> {
            override fun create(): T {
                return dependencyProducer.invoke()
            }
        }
    }

    inline fun <reified T : Any> singleton(crossinline dependencyProducer: () -> T) {
        /*
        вариант фабрики, которая создаёт новый объект только при первом вызове create(), 
        при последующих возвращает ранее созданый экземпляр
        */
        map[T::class] = object : Factory<T> {
            private var _dependency: T? = null

            override fun create(): T {
                /*
                двойная проверка с synchronized() блоком нужна чтобы случайно 
                не создать больше одного экземпляра объекта,
                такое возможно если код будет выполняться на разных потоках,
                при хорошем проектировании такой исход маловероятен
                */
                _dependency?.let { return it }
                synchronized(this) {
                    _dependency?.let { return it }
                    val dependency = dependencyProducer.invoke()
                    _dependency = dependency
                    return dependency
                }
            }
        }
    }

}


```

Небольшой пример как всё это используется (подробнее читайте исходники репозитория):

```
// зависимости app модуля
fun DI.initAppDependencies(applicationContext: Context) {
    singleton {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            AppDatabase.NAME
        ).build()
    }
    factory { instance<AppDatabase>().postDao() }
}

// зависимости core модуля
fun DI.initCoreDependencies() {
    factory {
        PostDeleteUseCase(instance())
    }
    factory {
        PostFetchAllUseCase(instance())
    }
}

class PostListViewModel : ViewModel() {

    /*
    вот так просто можно получить нужный экземпляр 
    и не париться где лежит экземпляр базы данных и сколько он живет
    */
    private val fetchAllUseCase: PostFetchAllUseCase = DI.instance()
    private val fetchDeleteUseCase: PostDeleteUseCase = DI.instance()

    ...

}
```
