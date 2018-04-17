package distudios.at.carcassonne.gui.lobby;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import distudios.at.carcassonne.R;

public class CreateGroupDialog extends DialogFragment {

    public OnCreateGroupEvents listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.dialog_create_group, null);

        setCancelable(false);
        builder.setTitle(R.string.text_create);
        builder.setView(view);
        builder.setPositiveButton(R.string.text_create, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText editText = view.findViewById(R.id.editText_groupName);
                if (listener != null) {
                    listener.onCreateGroup(editText.getText().toString());
                }
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public interface OnCreateGroupEvents {
        void onCreateGroup(String groupName);
    }

}
