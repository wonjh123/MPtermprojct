// Generated by view binder compiler. Do not edit!
package kr.ac.gachon.recommendate.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import kr.ac.gachon.recommendate.R;

public final class RegisterPopupBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final EditText enterCheck;

  @NonNull
  public final EditText enterEmail;

  @NonNull
  public final EditText enterName;

  @NonNull
  public final EditText enterPw;

  @NonNull
  public final Button registerBtn;

  private RegisterPopupBinding(@NonNull LinearLayout rootView, @NonNull EditText enterCheck,
      @NonNull EditText enterEmail, @NonNull EditText enterName, @NonNull EditText enterPw,
      @NonNull Button registerBtn) {
    this.rootView = rootView;
    this.enterCheck = enterCheck;
    this.enterEmail = enterEmail;
    this.enterName = enterName;
    this.enterPw = enterPw;
    this.registerBtn = registerBtn;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static RegisterPopupBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static RegisterPopupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.register_popup, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static RegisterPopupBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.enter_check;
      EditText enterCheck = ViewBindings.findChildViewById(rootView, id);
      if (enterCheck == null) {
        break missingId;
      }

      id = R.id.enter_email;
      EditText enterEmail = ViewBindings.findChildViewById(rootView, id);
      if (enterEmail == null) {
        break missingId;
      }

      id = R.id.enter_name;
      EditText enterName = ViewBindings.findChildViewById(rootView, id);
      if (enterName == null) {
        break missingId;
      }

      id = R.id.enter_pw;
      EditText enterPw = ViewBindings.findChildViewById(rootView, id);
      if (enterPw == null) {
        break missingId;
      }

      id = R.id.register_btn;
      Button registerBtn = ViewBindings.findChildViewById(rootView, id);
      if (registerBtn == null) {
        break missingId;
      }

      return new RegisterPopupBinding((LinearLayout) rootView, enterCheck, enterEmail, enterName,
          enterPw, registerBtn);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
