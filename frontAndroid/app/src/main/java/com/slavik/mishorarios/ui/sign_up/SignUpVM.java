package com.slavik.mishorarios.ui.sign_up;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.slavik.mishorarios.data.local.Repository;
import com.slavik.mishorarios.data.remote.ServiceProvider;
import com.slavik.mishorarios.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpVM extends ViewModel {

    private String name = "", lastName = "", email = "", passwd = "", passwdRepeat = "";

    private MutableLiveData<String> error = new MutableLiveData<>("");
    private MutableLiveData<Boolean> isSuccessful = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isSignUpEnabled = new MutableLiveData<>(false);

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<Boolean> getIsSuccessful() {
        return isSuccessful;
    }

    public LiveData<Boolean> getIsSignUpEnabled() {
        return isSignUpEnabled;
    }

    public void setName(String name) {
        this.name = name;
        updateIsSignUpEnabled();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        updateIsSignUpEnabled();
    }

    public void setEmail(String email) {
        this.email = email;
        updateIsSignUpEnabled();
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
        updateIsSignUpEnabled();
    }

    public void setPasswdRepeat(String passwdRepeat) {
        this.passwdRepeat = passwdRepeat;
        updateIsSignUpEnabled();
    }

    private void updateIsSignUpEnabled() {
        if (name.length() == 0 || lastName.length() == 0 || email.length() == 0 ||
                passwd.length() == 0 || passwdRepeat.length() == 0){
            isSignUpEnabled.postValue(false);
            return;
        }

        if (!passwd.equals(passwdRepeat)) {
            isSignUpEnabled.postValue(false);
            return;
        }

        isSignUpEnabled.postValue(true);
    }

    public void signUp() {
        User user = new User(name, lastName, email, passwd);
        ServiceProvider.getInstance().getUserService().signUp(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    showError("Ocurrió un error");
                    return;
                }

                Repository.getInstance().setUser(response.body());
                isSuccessful.postValue(true);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void showError(String message) {
        error.postValue(message);
    }
}
