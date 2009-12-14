package Administracion.actions;

import Grafico.MainWindow;

public class NewDiagramLogic {
    
    public void action() {
	MainWindow._diagrams.addTabItem();
	MainWindow.getComponents().guardarDisable(true);
//	MainWindow.getComponents().disableAll(true);
    }
}
