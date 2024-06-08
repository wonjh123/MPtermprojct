// Generated by view binder compiler. Do not edit!
package kr.ac.gachon.recommendate.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import kr.ac.gachon.recommendate.R;

public final class ShowCourselistBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ListView courseListView;

  @NonNull
  public final HeaderButtonTextviewLayoutBinding header;

  private ShowCourselistBinding(@NonNull LinearLayout rootView, @NonNull ListView courseListView,
      @NonNull HeaderButtonTextviewLayoutBinding header) {
    this.rootView = rootView;
    this.courseListView = courseListView;
    this.header = header;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ShowCourselistBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ShowCourselistBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.show_courselist, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ShowCourselistBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.course_listView;
      ListView courseListView = ViewBindings.findChildViewById(rootView, id);
      if (courseListView == null) {
        break missingId;
      }

      id = R.id.header;
      View header = ViewBindings.findChildViewById(rootView, id);
      if (header == null) {
        break missingId;
      }
      HeaderButtonTextviewLayoutBinding binding_header = HeaderButtonTextviewLayoutBinding.bind(header);

      return new ShowCourselistBinding((LinearLayout) rootView, courseListView, binding_header);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
