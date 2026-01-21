package adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rios_martinez_andre_pmdm_tarea5.R;

import java.util.List;

import modelo.Contactos;

public class ContactosAdapter extends RecyclerView.Adapter<ContactosAdapter.ViewHolder> {

    private Context context;
    private List<Contactos> listaContactos;

    public ContactosAdapter(Context context, List<Contactos> listaContactos) {
        this.context = context;
        this.listaContactos = listaContactos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_contacto, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Contactos contacto = listaContactos.get(position);
        holder.tvNombre.setText(contacto.getNombre());
    }

    @Override
    public int getItemCount() {
        return listaContactos != null ?  listaContactos.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombre;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
        }
    }
}