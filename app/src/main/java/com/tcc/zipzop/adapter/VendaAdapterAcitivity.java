package com.tcc.zipzop.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tcc.zipzop.R;
import com.tcc.zipzop.entity.Venda;
import com.tcc.zipzop.typeconverter.DateTimeConverter;
import com.tcc.zipzop.typeconverter.MoneyConverter;

import java.util.List;

public class VendaAdapterAcitivity extends BaseAdapter {
    private Context context;
    private List<Venda> vendaList;

    public VendaAdapterAcitivity(Context context, List<Venda> vendaList) {
        this.context = context;
        this.vendaList = vendaList;
    }


    @Override
    public int getCount() {
        return vendaList.size();
    }

    @Override
    public Object getItem(int position) {
        return vendaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.activity_venda_adapter, null);
        TextView campohora = (TextView) view.findViewById(R.id.horarioVenda);
        TextView campoPago = (TextView) view.findViewById(R.id.pago);
        TextView campototal = (TextView) view.findViewById(R.id.total);

        campohora.setText(DateTimeConverter.horaFormatada(vendaList.get(position).getDataPagamento()));
        campoPago.setText("R$"+MoneyConverter.toString(vendaList.get(position).getValorPago()));
        campototal.setText("R$"+MoneyConverter.toString(vendaList.get(position).getValorVenda()));
        return view;
    }
}
