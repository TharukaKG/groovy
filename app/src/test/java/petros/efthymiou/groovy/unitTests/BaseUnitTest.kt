package petros.efthymiou.groovy.unitTests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import petros.efthymiou.groovy.utils.MainCoroutineScopeRule

open class BaseUnitTest {

    @get:Rule
    var coroutineTestRule = MainCoroutineScopeRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

}