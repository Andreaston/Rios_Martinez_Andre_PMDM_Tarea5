package adaptador;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import coil.Coil;
import coil.request.ImageRequest;

import com.example.rios_martinez_andre_pmdm_tarea5.AgregarContactoActivity;
import com.example.rios_martinez_andre_pmdm_tarea5.R;

import java.util.List;

import database.ContactosDB;
import modelo.Contactos;

public class ContactosAdapter extends RecyclerView.Adapter<ContactosAdapter.ViewHolder> {

    private Context context;
    private List<Contactos> listaContactos;
    private ContactosDB contactosDB;

    public ContactosAdapter(Context context, List<Contactos> listaContactos, ContactosDB contactosDB) {
        this.context = context;
        this.listaContactos = listaContactos;
        this.contactosDB = new ContactosDB(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_contacto, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Contactos contacto = listaContactos.get(position);

        String url = contacto.getFoto();

        if (url != null && !url.isEmpty()){
            coil.Coil.imageLoader(context).enqueue(
                    new coil.request.ImageRequest.Builder(context)
                            .data(url)
                            .target(holder.imgContacto)
                            .build()
            );
        }else {
            holder.imgContacto.setImageResource(R.drawable.ic_launcher_foreground);
        }

        holder.tvNombre.setText(contacto.getNombre());
        holder.tvTelefono.setText(contacto.getTelefono());

        holder.btnMenu.setOnClickListener(v -> {

            PopupMenu popup = new PopupMenu(context, holder.btnMenu);
            popup.inflate(R.menu.menu_popup);

            popup.setOnMenuItemClickListener(item -> {
                int itemId = item.getItemId();

                if (itemId == R.id.Editar) {
                    Intent intent = new Intent(context, AgregarContactoActivity.class);
                    intent.putExtra("id", contacto.getId());
                    context.startActivity(intent);
                    return true;
                }

                if (itemId == R.id.Eliminar) {
                    contactosDB.eliminarContacto(contacto.getId());
                    listaContactos.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, listaContactos.size());
                    return true;
                }

                return false;
            });

            popup.show();
        });
    }

    @Override
    public int getItemCount() {
        return listaContactos != null ?  listaContactos.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombre, tvTelefono;
        ImageView btnMenu, imgContacto;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvTelefono = itemView.findViewById(R.id.tvTelefono);
            btnMenu = itemView.findViewById(R.id.btnMenu);
            imgContacto = itemView.findViewById(R.id.imgContacto);
        }
    }
}