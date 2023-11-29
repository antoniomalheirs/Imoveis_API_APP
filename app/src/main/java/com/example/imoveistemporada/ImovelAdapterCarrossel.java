package com.example.imoveistemporada;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ImovelAdapterCarrossel extends RecyclerView.Adapter<ImovelAdapterCarrossel.ImovelViewHolder> {
    private List<Imovel> imoveisList;

    public ImovelAdapterCarrossel(List<Imovel> imoveisList) {
        this.imoveisList = imoveisList;
    }

    @NonNull
    @Override
    public ImovelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Use o layout do item_imovel_carrossel.xml
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_imovelcarrossel, parent, false);
        return new ImovelViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImovelViewHolder holder, int position) {
        // Ajuste a lógica conforme necessário
        Imovel imovel = imoveisList.get(position);
        holder.bind(imovel);
    }

    @Override
    public int getItemCount() {
        return imoveisList.size();
    }

    public class ImovelViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textViewNomeProprietario;
        private TextView textViewValorDiaria;
        private TextView textViewCep;
        private TextView textViewPropemail;

        public ImovelViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewNomeProprietario = itemView.findViewById(R.id.textViewNomeProprietario);
            textViewValorDiaria = itemView.findViewById(R.id.textViewValorDiaria);
            textViewCep = itemView.findViewById(R.id.textViewCep);
            textViewPropemail = itemView.findViewById(R.id.textViewPropemail);
            // Adicione outros elementos conforme necessário
        }

        public void bind(Imovel imovel) {
            // Se a imagem for um recurso de origem (por exemplo, R.mipmap.pngegg), use setImageResource
            // Se a imagem for uma URL, você pode usar bibliotecas como Picasso ou Glide.
            // Exemplo com Picasso: Picasso.get().load(imovel.getUrlImagem()).into(imageView);
            imageView.setImageResource(R.mipmap.pngegg);
            textViewNomeProprietario.setText("Proprietário - " + imovel.getNomeProprietario());
            textViewValorDiaria.setText("Valor R$ - " + imovel.getValorDiaria());
            textViewCep.setText("CEP -- " + imovel.getCep());

            // Configure o e-mail do proprietário alinhado à direita
            textViewPropemail.setText("Contato Anunciante -- " + imovel.getProprietario().getEmail());
            textViewPropemail.setGravity(Gravity.END); // Alinha à direita
        }
    }
}
