import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class App extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JCheckBox direccionadoCheckBox;
    private JCheckBox conPesosCheckBox;
    private JButton crearGrafoButton;
    private JButton quemarButton;
    private JTextField textFieldVertice;
    private JButton insertarButton;
    private JComboBox comboBoxAristaInicio;
    private JTextField textFieldPeso;
    private JButton insertarButton1;
    private JButton mostrarGrafoButton;
    private JComboBox comboBoxVerticeInicial;
    private JButton DFSButton;
    private JButton BFSButton;
    private JButton dijkstraButton;
    private JTextArea textArea1;
    private JComboBox comboBoxAristaFin;
    private JButton eliminarButton;
    Grafo grafo;

    public App(){
        setContentPane(panel1);
        crearGrafoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearGrafo();
            }
        });
        quemarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quemarDatos();
            }
        });
        mostrarGrafoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarGrafo();
            }
        });
        insertarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertarVertice();
            }
        });
        insertarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertarArista();
            }
        });
        DFSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dfs();
            }
        });
        BFSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bsf();
            }
        });
        dijkstraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dijkstra();
            }
        });
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarVertice();
            }
        });
    }

    public void crearGrafo(){
        grafo = new Grafo(direccionadoCheckBox.isSelected(), conPesosCheckBox.isSelected());

        if(!conPesosCheckBox.isSelected()){
            textFieldPeso.setEnabled(false);
            textFieldPeso.setText("1");
        }else{
            textFieldPeso.setEnabled(true);
            textFieldPeso.setText("");
        }

        resetearApp();
        activarCampos();

        JOptionPane.showMessageDialog(null, "Grafo creado correctamente");
    }

    public void quemarDatos(){
        grafo.addVertice("1");
        actualizarCombos("1", true);
        grafo.addVertice("2");
        actualizarCombos("2", true);
        grafo.addVertice("3");
        actualizarCombos("3", true);
        grafo.addVertice("4");
        actualizarCombos("4", true);
        grafo.addVertice("5");
        actualizarCombos("5", true);

        grafo.addEdge(grafo.getVertices().get(0).getData(), grafo.getVertices().get(1).getData(), 1);
        grafo.addEdge(grafo.getVertices().get(0).getData(), grafo.getVertices().get(2).getData(), 1);

        grafo.addEdge(grafo.getVertices().get(1).getData(), grafo.getVertices().get(2).getData(), 1);
        grafo.addEdge(grafo.getVertices().get(1).getData(), grafo.getVertices().get(3).getData(), 1);

        grafo.addEdge(grafo.getVertices().get(2).getData(), grafo.getVertices().get(4).getData(), 1);

        quemarButton.setEnabled(false);
        mostrarGrafo();
    }

    public void mostrarGrafo(){
        textArea1.setText(grafo.print());
    }

    public void actualizarCombos(String data, boolean bool){

        if(bool){
            comboBoxAristaInicio.addItem(data);
            comboBoxAristaFin.addItem(data);
            comboBoxVerticeInicial.addItem(data);
        }else{
            comboBoxAristaInicio.removeItem(data);
            comboBoxAristaFin.removeItem(data);
            comboBoxVerticeInicial.removeItem(data);
        }

    }

    public void insertarVertice(){

        if(!textFieldVertice.getText().isEmpty()){
            if(grafo.getVertexByValue(textFieldVertice.getText()) == null){
                grafo.addVertice(textFieldVertice.getText());
                actualizarCombos(textFieldVertice.getText(), true);
                mostrarGrafo();
                textFieldVertice.setText("");
                JOptionPane.showMessageDialog(null, "Vertice insertado exitosamente");
            }else{
                JOptionPane.showMessageDialog(null, "Error. El vertice ya ha sido creado.");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Error. No se puede ingresar un vertice nulo.");
        }

    }

    public void eliminarVertice(){
        if(!textFieldVertice.getText().isEmpty()){
            if(grafo.getVertexByValue(textFieldVertice.getText()) != null){
                grafo.removeVertice(grafo.getVertexByValue(textFieldVertice.getText()));
                actualizarCombos(textFieldVertice.getText(), false);
                mostrarGrafo();
                textFieldVertice.setText("");
                JOptionPane.showMessageDialog(null, "Vertice ha sido eliminado exitosamente");
            }else{
                JOptionPane.showMessageDialog(null, "Error. El vertice no existe.");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Error. No se puede eliminar un vertice nulo.");
        }
    }

    public void insertarArista(){
        String inicio = comboBoxAristaInicio.getSelectedItem().toString();
        String fin = comboBoxAristaFin.getSelectedItem().toString();

        if(comboBoxAristaInicio.getSelectedItem() != null && comboBoxAristaFin.getSelectedItem() != null){
            if(!textFieldPeso.getText().isEmpty()){
                if(inicio.equals(fin)) {
                    JOptionPane.showMessageDialog(null, "Error. Un vértice no puede tener una arista con sí mismo.");
                } else {
                    Vertice verticeInicio = grafo.getVertexByValue(inicio);
                    Vertice verticeFin = grafo.getVertexByValue(fin);

                    boolean edgeYaExiste = false;
                    for (Edge edge : verticeInicio.getEdges()) {
                        if (edge.getFinalVertice().equals(verticeFin)) {
                            edgeYaExiste = true;
                            break;
                        }
                    }

                    if (edgeYaExiste) {
                        JOptionPane.showMessageDialog(null, "Error. La arista ya existe.");
                    } else {
                        grafo.addEdge(inicio, fin, Integer.parseInt(textFieldPeso.getText()));
                        mostrarGrafo();
                        JOptionPane.showMessageDialog(null, "Vertice insertado exitosamente");
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null, "Error. No se puede ingresar una arista con peso nulo.");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Error. No se puede ingresar una arista con un vértice vacío");
        }
    }

    public void dfs(){
        if(comboBoxVerticeInicial.getSelectedItem() != null){
            ArrayList<Vertice> visitedVertices = new ArrayList<>();
            textArea1.setText(grafo.depthFirstTraversal(grafo.getVertexByValue(comboBoxVerticeInicial.getSelectedItem().toString()), visitedVertices));
        }else{
            JOptionPane.showMessageDialog(null, "Error. Debe seleccionar un vértice inicial.");
        }
    }

    public void bsf(){
        if(comboBoxVerticeInicial.getSelectedItem() != null){
            textArea1.setText(grafo.breadthFirstTraversal(grafo.getVertexByValue(comboBoxVerticeInicial.getSelectedItem().toString())));
        }else{
            JOptionPane.showMessageDialog(null, "Error. Debe seleccionar un vértice inicial.");
        }
    }

    public void dijkstra(){
        if(comboBoxVerticeInicial.getSelectedItem() != null){
            textArea1.setText(grafo.dijkstra(grafo.getVertexByValue(comboBoxVerticeInicial.getSelectedItem().toString())));
        }else{
            JOptionPane.showMessageDialog(null, "Error. Debe seleccionar un vértice inicial.");
        }
    }

    public void resetearApp(){
        direccionadoCheckBox.setSelected(false);
        conPesosCheckBox.setSelected(false);
        textFieldVertice.setText("");
        comboBoxAristaInicio.removeAllItems();
        comboBoxAristaFin.removeAllItems();
        comboBoxVerticeInicial.removeAllItems();
        textArea1.setText("");
    }

    public void activarCampos(){
        quemarButton.setEnabled(true);
        textFieldVertice.setEnabled(true);
        eliminarButton.setEnabled(true);
        insertarButton.setEnabled(true);
        comboBoxAristaInicio.setEnabled(true);
        comboBoxAristaFin.setEnabled(true);
        insertarButton1.setEnabled(true);
        mostrarGrafoButton.setEnabled(true);
        comboBoxVerticeInicial.setEnabled(true);
        DFSButton.setEnabled(true);
        BFSButton.setEnabled(true);
        dijkstraButton.setEnabled(true);
    }
}
