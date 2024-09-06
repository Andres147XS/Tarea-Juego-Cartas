import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Jugador {

    private static final int TOTAL_CARTAS = 10;
    private static final int MARGEN = 10;
    private static final int DISTANCIA = 50;

    private Carta[] cartas = new Carta[TOTAL_CARTAS];
    private Random r = new Random();

    public void repartir() {
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        int i = 0;
        for (Carta c : cartas) {
            c.mostrar(pnl, MARGEN + i++ * DISTANCIA, MARGEN);
        }
        pnl.repaint();
    }

    public List<List<Carta>> obtenerFiguras() {
        List<List<Carta>> figuras = new ArrayList<>();
        List<Carta> cartasOrdenadas = new ArrayList<>();
        Collections.addAll(cartasOrdenadas, cartas);

        cartasOrdenadas.sort((c1, c2) -> {
            int pintaCompare = c1.getPinta().compareTo(c2.getPinta());
            if (pintaCompare != 0) return pintaCompare;
            return Integer.compare(c1.getNombre().ordinal(), c2.getNombre().ordinal());
        });

        for (int i = 0; i < cartasOrdenadas.size(); i++) {
            List<Carta> secuencia = new ArrayList<>();
            Carta carta = cartasOrdenadas.get(i);
            secuencia.add(carta);

            for (int j = i + 1; j < cartasOrdenadas.size(); j++) {
                Carta siguienteCarta = cartasOrdenadas.get(j);
                if (siguienteCarta.getPinta() == carta.getPinta() &&
                    siguienteCarta.getNombre().ordinal() == carta.getNombre().ordinal() + 1) {
                    secuencia.add(siguienteCarta);
                    carta = siguienteCarta;
                } else {
                    break;
                }
            }

            if (secuencia.size() >= 3) {
                figuras.add(secuencia);
            }
        }

        return figuras;
    }

    public int calcularPuntaje() {
        int puntaje = 0;
        for (Carta carta : cartas) {
            int valor;
            switch (carta.getNombre()) {
                case AS:
                case JACK:
                case REINA:
                case REY:
                    valor = 10;
                    break;
                default:
                    valor = carta.getNombre().ordinal() + 1;
                    break;
            }
            puntaje += valor;
        }
        return puntaje;
    }
}