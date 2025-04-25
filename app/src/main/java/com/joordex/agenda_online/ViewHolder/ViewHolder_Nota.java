package com.joordex.agenda_online.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joordex.agenda_online.R;

public class ViewHolder_Nota extends RecyclerView.ViewHolder {

    View mView;

    private ViewHolder_Nota.ClickListener mClickListener;

    public interface ClickListener{
        void onItemClick(View view, int position); /*Se ejecuta al presionar el item */
        void onItemLongClick(View view, int position); /*Se ejecuta al dejar presionado el item */
    }

    public void setOnClickListener(ViewHolder_Nota.ClickListener clickListener){
        mClickListener = clickListener;
    }
    public ViewHolder_Nota(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongClick(view, getAdapterPosition());
                return false;
            }
        });
    }

    public void SetearDatos(Context context, String id_nota, String uid_usuario, String correo_usuario,
                            String fecha_hora_registro, String titulo, String descripcion, String fecha_nota,
                            String estado){
        //DECLARAR VISTAS
        TextView Id_nota_item, Uid_usuario_item, Correo_usuario_item, Fecha_hora_registro_item,
                Titulo_item, Descripcion_item, Fecha_item, Estado_item;

        ImageView Tarea_finalizada_item, Tarea_no_finalizada_item;

        //ESTABLECER LA CONEXION CON LOS ITEMS
        Id_nota_item = mView.findViewById(R.id.Id_nota_item);
        Uid_usuario_item = mView.findViewById(R.id.Uid_usuario_item);
        Correo_usuario_item = mView.findViewById(R.id.Correo_usuario_item);
        Fecha_hora_registro_item = mView.findViewById(R.id.Fecha_hora_registro_item);
        Titulo_item = mView.findViewById(R.id.Titulo_item);
        Descripcion_item = mView.findViewById(R.id.Descripcion_item);
        Fecha_item = mView.findViewById(R.id.Fecha_item);
        Estado_item = mView.findViewById(R.id.Estado_item);

        Tarea_finalizada_item = mView.findViewById(R.id.Tarea_finalizada_item);
        Tarea_no_finalizada_item = mView.findViewById(R.id.Tarea_no_finalizada_item);

        //SETEAR
        Id_nota_item.setText(id_nota);
        Uid_usuario_item.setText(uid_usuario);
        Correo_usuario_item.setText(correo_usuario);
        Fecha_hora_registro_item.setText(fecha_hora_registro);
        Titulo_item.setText(titulo);
        Descripcion_item.setText(descripcion);
        Fecha_item.setText(fecha_nota);
        Estado_item.setText(estado);

        //GESTIONAR COLOR DEL ESTADO
        if (estado.equals("Finalizado")){
            Tarea_finalizada_item.setVisibility(View.VISIBLE);
        } else {
            Tarea_no_finalizada_item.setVisibility(View.VISIBLE);
        }







    }
}
