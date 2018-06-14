package distudios.at.carcassonne.gui.field;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

import distudios.at.carcassonne.CarcassonneApp;
import distudios.at.carcassonne.R;
import distudios.at.carcassonne.engine.logic.Card;
import distudios.at.carcassonne.engine.logic.CardDataBase;
import distudios.at.carcassonne.engine.logic.ExtendedCard;
import distudios.at.carcassonne.engine.logic.GameController;
import distudios.at.carcassonne.engine.logic.IGameController;
import distudios.at.carcassonne.engine.logic.Orientation;
import distudios.at.carcassonne.engine.logic.PeepPosition;

public class PlacePeepDialog extends DialogFragment implements View.OnClickListener{

    public GameFragment fragment;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();


        View v = inflater.inflate(R.layout.place_peep, null);

        v.findViewById(R.id.button_topleft).setOnClickListener(this);

        ImageView currentCard = v.findViewById(R.id.image_currentcard);

        IGameController controller = CarcassonneApp.getGameController();
        Card c = controller.getCurrentCard();
        ExtendedCard ec = CardDataBase.getCardById(c.getId());
        Bitmap source = PlayfieldView.cardIdToBitmap(ec.getId());

        Matrix m = new Matrix();
        if (c.getOrientation() == Orientation.NORTH) {
            m.postRotate(0);
        } else if (c.getOrientation() == Orientation.EAST) {
            m.postRotate(90);
        } else if (c.getOrientation() == Orientation.SOUTH) {
            m.postRotate(180);
        } else {
            m.postRotate(270);
        }
        Bitmap rotated = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), m, true);
        currentCard.setImageBitmap(rotated);

        Button topleft = v.findViewById(R.id.button_topleft);
        Button top = v.findViewById(R.id.button_top);
        Button topright = v.findViewById(R.id.button_topright);
        Button left = v.findViewById(R.id.button_left);
        Button center = v.findViewById(R.id.button_center);
        Button right = v.findViewById(R.id.button_right);
        Button bottomleft = v.findViewById(R.id.button_bottomleft);
        Button bottom = v.findViewById(R.id.button_bottom);
        Button bottomright = v.findViewById(R.id.button_bottomright);

        topleft.setEnabled(false);
        topleft.setOnClickListener(this);
        top.setEnabled(false);
        top.setOnClickListener(this);
        topright.setEnabled(false);
        topright.setOnClickListener(this);
        left.setEnabled(false);
        left.setOnClickListener(this);
        center.setEnabled(false);
        center.setOnClickListener(this);
        right.setEnabled(false);
        right.setOnClickListener(this);
        bottomleft.setEnabled(false);
        bottomleft.setOnClickListener(this);
        bottom.setEnabled(false);
        bottom.setOnClickListener(this);
        bottomright.setEnabled(false);
        bottomright.setOnClickListener(this);

        for (PeepPosition p : controller.showPossibleFigurePos(c)) {
            if (p == PeepPosition.TopLeft) {
                topleft.setEnabled(true);
            } else if (p == PeepPosition.Top) {
                top.setEnabled(true);
            } else if (p == PeepPosition.TopRight) {
                topright.setEnabled(true);
            } else if (p == PeepPosition.Left) {
                left.setEnabled(true);
            } else if (p == PeepPosition.Right) {
                center.setEnabled(true);
            } else if (p == PeepPosition.Center) {
                right.setEnabled(true);
            } else if (p == PeepPosition.BottomLeft) {
                bottomleft.setEnabled(true);
            } else if (p == PeepPosition.Bottom) {
                bottom.setEnabled(true);
            } else {
                bottomright.setEnabled(true);
            }
        }

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(v)
                .setNegativeButton(R.string.text_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PlacePeepDialog.this.getDialog().cancel();
                    }
                })
        .setTitle(R.string.text_state_place_peep);
        return builder.create();
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        IGameController controller = CarcassonneApp.getGameController();
        Card current = controller.getCurrentCard();
        if (id == R.id.button_topleft) {
            controller.placeFigure(current, PeepPosition.TopLeft);
        } else if (id == R.id.button_top) {
            controller.placeFigure(current, PeepPosition.Top);
        } else if (id == R.id.button_topright) {
            controller.placeFigure(current, PeepPosition.TopRight);
        } else if (id == R.id.button_left) {
            controller.placeFigure(current, PeepPosition.Left);
        } else if (id == R.id.button_center) {
            controller.placeFigure(current, PeepPosition.Center);
        } else if (id == R.id.button_right) {
            controller.placeFigure(current, PeepPosition.Right);
        } else if (id == R.id.button_bottomleft) {
            controller.placeFigure(current, PeepPosition.BottomLeft);
        } else if (id == R.id.button_bottom) {
            controller.placeFigure(current, PeepPosition.Bottom);
        } else {
            controller.placeFigure(current, PeepPosition.BottomRight);
        }
        fragment.updateFromGameState();
        PlacePeepDialog.this.getDialog().cancel();
    }
}
