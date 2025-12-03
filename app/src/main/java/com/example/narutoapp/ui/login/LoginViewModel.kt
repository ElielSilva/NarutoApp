package com.example.narutoapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.narutoapp.models.LoginUiState
import com.example.narutoapp.repository.auth.AuthRepository
import com.example.narutoapp.repository.auth.AuthRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    private val authRepository: AuthRepository = AuthRepositoryImpl()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, emailError = null) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, passwordError = null) }
    }

    fun onLoginClick() {
        val email = _uiState.value.email
        val password = _uiState.value.password

        val emailError = validateEmail(email)
        val passwordError = validatePassword(password)

        if (emailError != null || passwordError != null) {
            _uiState.update {
                it.copy(
                    emailError = emailError,
                    passwordError = passwordError
                )
            }
            return
        }
        performLogin(email, password)
    }

    private fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "Email é obrigatório"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                "Email inválido"
            else -> null
        }
    }

    private fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "Senha é obrigatória"
            password.length < 6 -> "Senha deve ter no mínimo 6 caracteres"
            else -> null
        }
    }

    private fun performLogin(email: String, password: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, loginError = null) }

            authRepository.login(email, password)
                .onSuccess { response ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isLoginSuccessful = true
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            loginError = error.message ?: "Erro desconhecido"
                        )
                    }
                }
        }
    }
}