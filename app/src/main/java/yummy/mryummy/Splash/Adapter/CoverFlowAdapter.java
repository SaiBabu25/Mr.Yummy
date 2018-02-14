package yummy.mryummy.Splash.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import yummy.mryummy.R;
import yummy.mryummy.Splash.Activity.AllBakeriesListActivity;
import yummy.mryummy.Splash.Activity.AllRestaurentsListActivity;
import yummy.mryummy.Splash.Activity.AllSweetsListActivity;
import yummy.mryummy.Splash.Activity.AllYummyListActivity;
import yummy.mryummy.Splash.Model.Game;

/**
 * Created by acer on 11/7/2017.
 */

public class CoverFlowAdapter extends BaseAdapter {

    private ArrayList<Game> data;
    private FragmentActivity activity;

    public CoverFlowAdapter(FragmentActivity context, ArrayList<Game> objects) {
        this.activity = context;
        this.data = objects;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Game getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        try {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                convertView = inflater.inflate(R.layout.item_cover, null, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.gameImage.setImageResource(data.get(position).getImageSource());
        viewHolder.gameName.setText(data.get(position).getName());
        }catch(OutOfMemoryError ome){

        }
        convertView.setOnClickListener(onClickListener(position));

        return convertView;
    }

    private View.OnClickListener onClickListener(final int position) {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
              /*  final Dialog dialog = new Dialog(activity);
                dialog.setContentView(R.layout.dialog_game_info);
                dialog.setCancelable(true); // dimiss when touching outside
                dialog.setTitle("Restaurent Details");

                TextView text = (TextView) dialog.findViewById(R.id.name);
                text.setText(getItem(position).getName());
                ImageView image = (ImageView) dialog.findViewById(R.id.image);
                image.setImageResource(getItem(position).getImageSource());

                dialog.show();*/


              /*  games.add(new Game(R.drawable.image_2, "Bakeries")); //0
                games.add(new Game(R.drawable.image_3, "Sweets")); //1
                games.add(new Game(R.drawable.image_1, "Restaurents")); //2
                games.add(new Game(R.drawable.image_4, "Yummy")); //3
                games.add(new Game(R.drawable.one, "MilkShakes")); //4
                */

                if (position == 0) {
                    Intent myIntent = new Intent(view.getContext(), AllBakeriesListActivity.class);
                    activity.startActivityForResult(myIntent,2);
                }
                if (position == 1) {
                    Intent myIntent = new Intent(view.getContext(), AllSweetsListActivity.class);
                    activity.startActivityForResult(myIntent,1);
                }
                if (position == 2) {
                    Intent myIntent = new Intent(view.getContext(), AllRestaurentsListActivity.class);
                    activity.startActivityForResult(myIntent,0);
                }
                if (position == 3) {
                    Intent myIntent = new Intent(view.getContext(), AllYummyListActivity.class);
                    activity.startActivityForResult(myIntent,3);
                }
                if (position == 4) {
                    Intent myIntent = new Intent(view.getContext(), AllRestaurentsListActivity.class);
                    activity.startActivityForResult(myIntent,3);
                }
            }
        };
    }


    private static class ViewHolder {
        private TextView gameName;
        private ImageView gameImage;

        public ViewHolder(View v) {
            try {
                gameImage = (ImageView) v.findViewById(R.id.image);
                gameName = (TextView) v.findViewById(R.id.name);
            }catch (NullPointerException npe){

            }
        }
    }
}
