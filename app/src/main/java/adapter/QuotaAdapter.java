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
import model.PayerCost;
import model.Quota;

public class QuotaAdapter  extends ArrayAdapter {
    private Context context;
    private List<PayerCost> items;
    private LayoutInflater mInflater;

    public QuotaAdapter(Context context, List<PayerCost> objects) {
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
        public final TextView _text_view_quotas;
        public final RelativeLayout rootView;

        private ViewHolder(TextView _text_view_quotas, RelativeLayout rootView) {
            this._text_view_quotas = _text_view_quotas;
            this.rootView = rootView;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            TextView _text_view_quotas = (TextView) rootView.findViewById(R.id._text_view_quotas);
            return new ViewHolder(_text_view_quotas, rootView);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout._adapter_list_view, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        PayerCost item = items.get(position);

        vh._text_view_quotas.setText(item.getRecommendedMessage());
        return vh.rootView;
    }
}
