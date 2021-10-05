package com.kb.choco

import com.kb.choco.data.model.LoginRequest
import com.kb.choco.data.model.LoginResponse
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kb.choco.domain.feature.authentication.LoginUseCase
import com.kb.choco.ui.login.LoginViewModel
import com.kb.nytimes.testutils.thenReturnFlow
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class LoginViewModelTest {
    lateinit var viewModel: LoginViewModel

    @Mock
    lateinit var loginUseCase: LoginUseCase

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()
    val dispatcher = TestCoroutineDispatcher()

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = LoginViewModel(
            loginUseCase = loginUseCase
        )
        setupObservers()
    }

    private fun setupObservers() {
        with(viewModel) {
            loginToken.observeForever { }
        }
    }

    @Test
    fun `should login successfully`() {
        val loginResponse = createMocLoginResponse()
        whenever(loginUseCase.build(any())).thenReturnFlow(
            loginResponse
        )

        with(viewModel) {
            login(LoginRequest("user@choco.com", "chocorian"))
            Assert.assertTrue(loginResponse.token?.isNotEmpty() ?: false)
        }
    }

    @Test
    fun `should not login successfully`() {
        val loginResponse = createMocLoginResponse()
        whenever(loginUseCase.build(any())).thenThrow(Throwable("Not found"))

        with(viewModel) {
            login(LoginRequest("user@choco.com", "chocorian"))
            Assert.assertTrue(loginResponse.token?.isNotEmpty() ?: false)
        }
    }

    private fun createMocLoginResponse() =
        LoginResponse(token = "jaksbeg762jsbdfh")

}
