package com.cloud2bubble.ptsense;

import java.util.HashMap;
import java.util.Map;

public class PTService {

	public static Map<String, Map<String, String[]>> SERVICES = new HashMap<String, Map<String, String[]>>();

	static {
		Map<String, String[]> STOPS_MPORTO = new HashMap<String, String[]>();
		STOPS_MPORTO.put("A",
				new String[] { "Estádio do Dragão", "Campanhã", "Heroísmo",
						"Campo 24 de Agosto", "Bolhão", "Trindade", "Lapa",
						"Carolina Michaelis", "Casa da Música", "Francos",
						"Ramalde", "Viso", "Sete Bicas", "Senhora da Hora",
						"Vasco da Gama", "Estádio do Mar", "Pedro Hispano",
						"Parque Real", "Câmara de Matosinhos",
						"Matosinhos Sul", "Brito Capelo", "Mercado",
						"Senhor de Matosinhos" });

		STOPS_MPORTO.put("B",
				new String[] { "Estádio do Dragão", "Campanhã", "Heroísmo",
						"Campo 24 de Agosto", "Bolhão", "Trindade", "Lapa",
						"Carolina Michaelis", "Casa da Música", "Francos",
						"Ramalde", "Viso", "Sete Bicas", "Senhora da Hora",
						"Fonte do Cuco", "Custóias", "Esposade", "Crestins",
						"Verdes", "Pedras Rubras", "Lidador",
						"Vilar do Pinheiro", "Modivas Sul", "Modivas Centro",
						"Mindelo", "Espaço Natureza", "Varziela", "Árvore",
						"Azurara", "Santa Clara", "Vila do Conde",
						"Alto do Pega", "Portas Fronhas", "São Brás",
						"Póvoa de Varzim" });

		STOPS_MPORTO.put("C", new String[] { "Campanhã", "Heroísmo",
				"Campo 24 de Agosto", "Bolhão", "Trindade", "Lapa",
				"Carolina Michaelis", "Casa da Música", "Francos", "Ramalde",
				"Viso", "Sete Bicas", "Senhora da Hora", "Fonte do Cuco",
				"Cândido dos Reis", "Pias", "Araujo", "Custió",
				"Parque da Maia", "Fórum da Maia", "Zona Industrial", "Mandim",
				"Castêlo da Maia", "ISMAI" });

		STOPS_MPORTO.put("D", new String[] { "Hospital de São João", "IPO",
				"Pólo Universitário", "Salgueiros", "Combatentes", "Marquês",
				"Faria Guimarães", "Trindade", "Aliados", "São Bento",
				"Jardim do Morro", "General Torres", "Câmara Gaia",
				"João de Deus", "D. João II", "Santo Ovídio" });

		STOPS_MPORTO.put("E", new String[] { "Estádio do Dragão", "Campanhã",
				"Heroísmo", "Campo 24 de Agosto", "Bolhão", "Trindade", "Lapa",
				"Carolina Michaelis", "Casa da Música", "Francos", "Ramalde",
				"Viso", "Sete Bicas", "Senhora da Hora", "Fonte do Cuco",
				"Custóias", "Esposade", "Crestins", "Verdes", "Botica",
				"Aeroporto" });

		STOPS_MPORTO
				.put("F", new String[] { "Fânzeres", "Venda Nova", "Carreira",
						"Baguim", "Campainha", "Rio Tinto", "Levada",
						"Nau Vitória", "Nasoni", "Contumil",
						"Estádio do Dragão", "Campanhã", "Heroísmo",
						"Campo 24 de Agosto", "Bolhão", "Trindade", "Lapa",
						"Carolina Michaelis", "Casa da Música", "Francos",
						"Ramalde", "Viso", "Sete Bicas", "Senhora da Hora" });

		Map<String, String[]> STOPS_STCP = new HashMap<String, String[]>();
		STOPS_STCP.put("1", new String[] { "Infante", "Alfândega",
				"Museu Vinho Porto", "Museu C. Eléctrico", "Bicalho",
				"Ponte Arrábida", "Sécil", "Gás", "Fluvial", "D.leonor",
				"Cantareira", "Passeio Alegre" });

		STOPS_STCP.put("10", new String[] { "Lameira", "Escola Primária",
				"R. Capela", "Belói", "Mó", "Terra Nova", "Carvalheiras",
				"Félix", "Camões", "Dr. Pedro Vitorino", "Escola Preparatoria",
				"Rio Ferreira", "Covilhã", "Fonte Telha", "Rosa Damasceno",
				"Farmacia", "1 Maio", "Vila Verde", "Ervedosa" });

		STOPS_STCP.put("18", new String[] { "Museu C. Eléctrico",
				"Entre Quintas", "Restauração", "Viriato", "Carmo" });

		STOPS_STCP.put("22", new String[] { "Carmo", "Clérigos",
				"Pr. Da Liberdade", "Batalha", "Batalha-guindais",
				"Santa Catarina", "Pr. D. João I", "Av. Aliados",
				"Pr. Filipa De Lencastre", "Guilherme Gomes Fernandes" });

		STOPS_STCP.put("55", new String[] { "Bolhão-mercado", "S.ta Catarina",
				"Largo Do Padrão", "Campo 24 De Agosto", "Nave", "Barros Lima",
				"Pr. Das Flores", "Av. 25 De Abril", "Fonte Velha",
				"Estádio Do Dragão", "Corujeira", "Ferr. Dos Santos", "Falcão",
				"Cartes", "Escolas", "S. Roque", "Tv. Da Ponte", "S. Caetano",
				"David Correia Silva", "D.sancho I", "Cruzeiro",
				"Igreja Rio Tinto 1", "Lourinha", "Perlinhas",
				"Rio Tinto (estação)", "Joaquim Neves", "Campainha",
				"Novo Cemitério", "Paço", "Quinta Do Paço", "Baguim",
				"Baguim (igreja)", "Escola Frei Manuel", "Sta. Inês",
				"Miguel Torga", "Quinta Da Missilva", "Missilva (piscinas)" });

		SERVICES.put("Metro do Porto", STOPS_MPORTO);
		SERVICES.put("STCP", STOPS_STCP);
	}

}
