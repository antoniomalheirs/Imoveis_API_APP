package com.example.imoveistemporada;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ImovelAdapter extends RecyclerView.Adapter<ImovelAdapter.ImovelViewHolder> {
    private List<Imovel> imoveisList;
    private OnImovelClickListener onImovelClickListener;

    public void setOnImovelClickListener(OnImovelClickListener listener) {
        this.onImovelClickListener = listener;
    }
    public ImovelAdapter(List<Imovel> imoveisList) {
        this.imoveisList = imoveisList;
    }

    @NonNull
    @Override
    public ImovelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_imovel, parent, false);
        return new ImovelViewHolder(itemView);
    }
    public interface OnImovelClickListener {
        void onImovelClick(Imovel imovel);
    }

    @Override
    public void onBindViewHolder(@NonNull ImovelViewHolder holder, int position) {
        Imovel imovel = imoveisList.get(position);
        holder.bind(imovel);
    }

    @Override
    public int getItemCount() {
        return imoveisList.size();
    }

    public class ImovelViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewIdImovel;
        private TextView textViewNomeProprietario;
        private TextView textViewValorDiaria;
        private TextView textViewContato;
        private TextView textViewCep;
        private TextView textViewCidade;
        private TextView textViewUid;
        private TextView textViewPropemail;

        public ImovelViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewIdImovel = itemView.findViewById(R.id.textViewIdImovel);
            textViewNomeProprietario = itemView.findViewById(R.id.textViewNomeProprietario);
            textViewValorDiaria = itemView.findViewById(R.id.textViewValorDiaria);
            textViewContato = itemView.findViewById(R.id.textViewContato);
            textViewCep = itemView.findViewById(R.id.textViewCep);
            textViewCidade = itemView.findViewById(R.id.textViewCidade);
            textViewUid = itemView.findViewById(R.id.textViewUid);
            textViewPropemail = itemView.findViewById(R.id.textViewPropemail);
            // Adicione outros TextViews conforme necessário para outros atributos do Imovel

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onImovelClickListener != null) {
                        onImovelClickListener.onImovelClick(imoveisList.get(position));
                    }
                }
            });

        }


        public void bind(Imovel imovel) {
            textViewIdImovel.setText("ID do Imóvel || " + imovel.getIdImovel());
            textViewNomeProprietario.setText("Proprietário - " + imovel.getNomeProprietario());
            textViewContato.setText("Contato - " + imovel.getTelefoneContato());
            textViewValorDiaria.setText("Valor R$ - " + imovel.getValorDiaria());
            textViewCep.setText("CEP -- " + imovel.getCep());
            textViewCidade.setText("Cidade -- " + imovel.getCidade());
            if (imovel.getProprietario() != null) {
                textViewUid.setText("Anunciante -- " + imovel.getProprietario().getUid());
                textViewPropemail.setText("E-mail -- " + imovel.getProprietario().getEmail());
            }
            // Configure outros TextViews conforme necessário para outros atributos do Imovel
        }
    }
}
