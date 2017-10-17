package Controller;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Created by Yifan on 10/16 016.
 */

public interface OnGetDataListener {
    public void onStart();

    public void onSuccess(DataSnapshot data);

    public void onFailed(DatabaseError databaseError);
}
