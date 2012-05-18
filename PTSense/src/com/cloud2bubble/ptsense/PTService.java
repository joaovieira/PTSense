package com.cloud2bubble.ptsense;

import java.util.HashMap;
import java.util.Map;

public class PTService {

	public static Map<String, Map<String, String[]>> SERVICES = new HashMap<String, Map<String, String[]>>();

	static {
		Map<String, String[]> STOPS_MPORTO = new HashMap<String, String[]>();
		STOPS_MPORTO.put("A",
				new String[] { "Est�dio do Drag�o", "Campanh�", "Hero�smo",
						"Campo 24 de Agosto", "Bolh�o", "Trindade", "Lapa",
						"Carolina Michaelis", "Casa da M�sica", "Francos",
						"Ramalde", "Viso", "Sete Bicas", "Senhora da Hora",
						"Vasco da Gama", "Est�dio do Mar", "Pedro Hispano",
						"Parque Real", "C�mara de Matosinhos",
						"Matosinhos Sul", "Brito Capelo", "Mercado",
						"Senhor de Matosinhos" });

		STOPS_MPORTO.put("B",
				new String[] { "Est�dio do Drag�o", "Campanh�", "Hero�smo",
						"Campo 24 de Agosto", "Bolh�o", "Trindade", "Lapa",
						"Carolina Michaelis", "Casa da M�sica", "Francos",
						"Ramalde", "Viso", "Sete Bicas", "Senhora da Hora",
						"Fonte do Cuco", "Cust�ias", "Esposade", "Crestins",
						"Verdes", "Pedras Rubras", "Lidador",
						"Vilar do Pinheiro", "Modivas Sul", "Modivas Centro",
						"Mindelo", "Espa�o Natureza", "Varziela", "�rvore",
						"Azurara", "Santa Clara", "Vila do Conde",
						"Alto do Pega", "Portas Fronhas", "S�o Br�s",
						"P�voa de Varzim" });

		STOPS_MPORTO.put("C", new String[] { "Campanh�", "Hero�smo",
				"Campo 24 de Agosto", "Bolh�o", "Trindade", "Lapa",
				"Carolina Michaelis", "Casa da M�sica", "Francos", "Ramalde",
				"Viso", "Sete Bicas", "Senhora da Hora", "Fonte do Cuco",
				"C�ndido dos Reis", "Pias", "Araujo", "Custi�",
				"Parque da Maia", "F�rum da Maia", "Zona Industrial", "Mandim",
				"Cast�lo da Maia", "ISMAI" });

		STOPS_MPORTO.put("D", new String[] { "Hospital de S�o Jo�o", "IPO",
				"P�lo Universit�rio", "Salgueiros", "Combatentes", "Marqu�s",
				"Faria Guimar�es", "Trindade", "Aliados", "S�o Bento",
				"Jardim do Morro", "General Torres", "C�mara Gaia",
				"Jo�o de Deus", "D. Jo�o II", "Santo Ov�dio" });

		STOPS_MPORTO.put("E", new String[] { "Est�dio do Drag�o", "Campanh�",
				"Hero�smo", "Campo 24 de Agosto", "Bolh�o", "Trindade", "Lapa",
				"Carolina Michaelis", "Casa da M�sica", "Francos", "Ramalde",
				"Viso", "Sete Bicas", "Senhora da Hora", "Fonte do Cuco",
				"Cust�ias", "Esposade", "Crestins", "Verdes", "Botica",
				"Aeroporto" });

		STOPS_MPORTO
				.put("F", new String[] { "F�nzeres", "Venda Nova", "Carreira",
						"Baguim", "Campainha", "Rio Tinto", "Levada",
						"Nau Vit�ria", "Nasoni", "Contumil",
						"Est�dio do Drag�o", "Campanh�", "Hero�smo",
						"Campo 24 de Agosto", "Bolh�o", "Trindade", "Lapa",
						"Carolina Michaelis", "Casa da M�sica", "Francos",
						"Ramalde", "Viso", "Sete Bicas", "Senhora da Hora" });

		Map<String, String[]> STOPS_STCP = new HashMap<String, String[]>();
		STOPS_STCP.put("1", new String[] { "Infante", "Alf�ndega",
				"Museu Vinho Porto", "Museu C. El�ctrico", "Bicalho",
				"Ponte Arr�bida", "S�cil", "G�s", "Fluvial", "D.leonor",
				"Cantareira", "Passeio Alegre" });

		STOPS_STCP.put("10", new String[] { "Lameira", "Escola Prim�ria",
				"R. Capela", "Bel�i", "M�", "Terra Nova", "Carvalheiras",
				"F�lix", "Cam�es", "Dr. Pedro Vitorino", "Escola Preparatoria",
				"Rio Ferreira", "Covilh�", "Fonte Telha", "Rosa Damasceno",
				"Farmacia", "1 Maio", "Vila Verde", "Ervedosa" });

		STOPS_STCP.put("18", new String[] { "Museu C. El�ctrico",
				"Entre Quintas", "Restaura��o", "Viriato", "Carmo" });

		STOPS_STCP.put("22", new String[] { "Carmo", "Cl�rigos",
				"Pr. Da Liberdade", "Batalha", "Batalha-guindais",
				"Santa Catarina", "Pr. D. Jo�o I", "Av. Aliados",
				"Pr. Filipa De Lencastre", "Guilherme Gomes Fernandes" });

		STOPS_STCP.put("55", new String[] { "Bolh�o-mercado", "S.ta Catarina",
				"Largo Do Padr�o", "Campo 24 De Agosto", "Nave", "Barros Lima",
				"Pr. Das Flores", "Av. 25 De Abril", "Fonte Velha",
				"Est�dio Do Drag�o", "Corujeira", "Ferr. Dos Santos", "Falc�o",
				"Cartes", "Escolas", "S. Roque", "Tv. Da Ponte", "S. Caetano",
				"David Correia Silva", "D.sancho I", "Cruzeiro",
				"Igreja Rio Tinto 1", "Lourinha", "Perlinhas",
				"Rio Tinto (esta��o)", "Joaquim Neves", "Campainha",
				"Novo Cemit�rio", "Pa�o", "Quinta Do Pa�o", "Baguim",
				"Baguim (igreja)", "Escola Frei Manuel", "Sta. In�s",
				"Miguel Torga", "Quinta Da Missilva", "Missilva (piscinas)" });

		SERVICES.put("Metro do Porto", STOPS_MPORTO);
		SERVICES.put("STCP", STOPS_STCP);
	}

}
