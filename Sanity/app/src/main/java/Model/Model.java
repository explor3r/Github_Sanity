package Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Yifan on 10/13 013.
 */

public class Model {
    protected String mUserID = Variable.GetInstance().getmUserID();
    protected DatabaseReference mDatabase;

    public void updateDBTime(){
        FirebaseDatabase.getInstance().getReference(mUserID).child("update").setValue(System.currentTimeMillis());
    }

}
