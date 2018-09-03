package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.henry.mercadopago.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import model.PayMedio;

public class PayMedioAdapter extends ArrayAdapter {
    private Context context;
    private List<PayMedio> items;
    private LayoutInflater mInflater;

    public PayMedioAdapter(Context context, List<PayMedio> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.items = objects;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int position) {
        Log.i("Posicion", position + " - ");return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        public final ImageView _image_view_pay_medio;
        public final TextView _text_view_pay_medio;
        public final RelativeLayout rootView;

        private ViewHolder(ImageView _image_view_pay_medio, TextView _text_view_pay_medio, RelativeLayout rootView) {
            this._image_view_pay_medio = _image_view_pay_medio;
            this._text_view_pay_medio = _text_view_pay_medio;
            this.rootView = rootView;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            ImageView _image_view_pay_medio = (ImageView) rootView.findViewById(R.id._image_view_pay_medio);
            TextView _text_view_pay_medio = (TextView) rootView.findViewById(R.id._text_view_pay_medio);
            return new ViewHolder(_image_view_pay_medio, _text_view_pay_medio, rootView);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout._adapter_grid_view, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        PayMedio item = items.get(position);

        vh._text_view_pay_medio.setText(item.getName());
        Picasso.with(context)
                .load(item.getSecureThumbnail())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(vh._image_view_pay_medio);
        return vh.rootView;
    }
}
