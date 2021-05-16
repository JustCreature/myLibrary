package com.example.mylibrary.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Context;
import android.content.Intent;
import android.util.Patterns;
import android.widget.Toast;

import com.example.mylibrary.MainActivity;
import com.example.mylibrary.data.LoginDataSource;
import com.example.mylibrary.data.LoginRepository;
import com.example.mylibrary.data.Result;
import com.example.mylibrary.data.model.LoggedInUser;
import com.example.mylibrary.R;
import com.example.mylibrary.registration.LoginPOST;

import java.io.IOException;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void loginRequest(Context postContext, String username, String password) {
        LoginPOST loginPOST = new LoginPOST(postContext);
        LoginDataSource loginDataSource = new LoginDataSource();

        String response;


        try {
            // TODO: handle loggedInUser authentication

            loginPOST.loginPOST(username, password,
                    new LoginPOST.VolleyResponseListener() {
                        @Override
                        public void OnError(String message) {
                            try {
                                throw new Throwable(message);
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }

                        @Override
                        public void OnResponse(String userId, String userInfo) {
                            Toast.makeText(postContext, "Success " + userInfo, Toast.LENGTH_SHORT).show();
                            loginDataSource.setUser(userId, userInfo);
                            login(postContext, userId, userInfo);
                        }
                    });


        } catch (Exception e) {

        }
    }

    public void login(Context context, String userId, String userInfo) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(userId, userInfo);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            System.out.println("AAAAAAAAAAAAA " + data);
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}