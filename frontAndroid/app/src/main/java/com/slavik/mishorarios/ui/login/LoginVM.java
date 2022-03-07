package com.slavik.mishorarios.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.slavik.mishorarios.data.local.Repository;
import com.slavik.mishorarios.data.remote.ServiceProvider;
import com.slavik.mishorarios.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginVM extends ViewModel {

    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private MutableLiveData<Boolean> logInCorrect = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> logInEnabled = new MutableLiveData<>(false);

    private String email = "", passwd = "";

    public LiveData<Boolean> getLogInEnabled() {
        return logInEnabled;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getLogInCorrect() {
        return logInCorrect;
    }

    public void logIn() {
        if (email.length() == 0 || passwd.length() == 0) return;

        ServiceProvider.getInstance()
                .getUserService().logIn(new User(email, passwd)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    showError();
                    return;
                }

                if (response.body().getId() == -1) {
                    showError("Los datos son incorrectos...");
                    return;
                }

                Repository.getInstance().setUser(response.body());
                logInCorrect.postValue(true);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                showError();
            }
        });

    }

    public void onInputChange(LoginInputs input, String value) {
        if (input == LoginInputs.email) email = value;
        else passwd = value;

        logInEnabled.postValue(email.length() > 0 && passwd.length() > 0);
    }

    private void showError() {
        showError("Ups...ocurrió un error");
    }

    private void showError(String message) {
        errorMessage.postValue(message);
        //errorMessage.postValue(""); // No es lo ideal... solo busco hacerlo rápido
    }
}
