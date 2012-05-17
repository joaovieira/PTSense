package com.cloud2bubble.ptsense;

import java.util.HashMap;
import java.util.Map;

public class PTService {
	
	public static Map<String, Map<String, String[]> > SERVICES = new HashMap<String, Map<String, String[]> >();
	
	static {
		Map<String, String[]> STOPS_MPORTO = new HashMap<String, String[]>();
		STOPS_MPORTO.put("Linha A", new String[] { "Est‡dio do Drag‹o",
    			"Campanh‹", "Hero’smo", "Campo 24 de Agosto", "Bolh‹o", "Trindade",
    			"Lapa", "Carolina Michaelis", "Casa da Mœsica", "Francos",
    			"Ramalde", "Viso", "Sete Bicas", "Senhora da Hora",
    			"Vasco da Gama", "Est‡dio do Mar", "Pedro Hispano", "Parque Real",
    			"C‰mara de Matosinhos", "Matosinhos Sul", "Brito Capelo",
    			"Mercado", "Senhor de Matosinhos" });
        
        SERVICES.put("Metro do Porto", STOPS_MPORTO);
    }

}
