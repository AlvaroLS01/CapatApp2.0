import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alvarols01.capatapp2.Hermandades
import com.alvarols01.capatapp2.R
import com.alvarols01.capatapp2.adapter.HermandadesAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FavoritosFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favoritos, container, false)
        recyclerView = view.findViewById(R.id.recyclerFavoritos)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        cargarFavoritos()
        return view
    }

    private fun cargarFavoritos() {
        val emailUsuario = FirebaseAuth.getInstance().currentUser?.email ?: return
        firestore.collection("usuarios").document(emailUsuario).collection("favoritos")
            .get()
            .addOnSuccessListener { result ->
                val hermandadesList = result.toObjects(Hermandades::class.java)
                recyclerView.adapter = HermandadesAdapter(hermandadesList) { nombreHermandad ->
                    // Navegar al detalle de la hermandad seleccionada
                    val action = FavoritosFragmentDirections.actionFavoritosFragmentToDetalleFragment2(nombreHermandad)
                    findNavController().navigate(action)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("FavoritosFragment", "Error al cargar favoritos: ", exception)
            }
    }
}