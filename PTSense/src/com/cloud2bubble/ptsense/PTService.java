package com.cloud2bubble.ptsense;

import java.util.HashMap;
import java.util.Map;

public class PTService {

	public static Map<String, Map<String, String[]>> SERVICES = new HashMap<String, Map<String, String[]>>();

	static {
		Map<String, String[]> STOPS_MPORTO = new HashMap<String, String[]>();
		STOPS_MPORTO.put("A", new String[] { "Estádio do Dragão", "Campanhã",
				"Heroísmo", "Campo 24 de Agosto", "Bolhão", "Trindade", "Lapa",
				"Carolina Michaelis", "Casa da Música", "Francos", "Ramalde",
				"Viso", "Sete Bicas", "Senhora da Hora", "Vasco da Gama",
				"Estádio do Mar", "Pedro Hispano", "Parque Real",
				"Câmara de Matosinhos", "Matosinhos Sul", "Brito Capelo",
				"Mercado", "Senhor de Matosinhos" });

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

		STOPS_STCP.put("61", new String[] { "Matosinhos Mercado", "S. Pedro",
				"Lota", "Godinho", "Matosinhos Praia", "Afonso Cordeiro",
				"Afonso Henriques", "Matosinhos (cmm)", "Goa",
				"Hospital Matosinhos", "Seara", "Sendim (cemitério)",
				"Sarilho", "R.lagoa", "C. Saude Da Sra. Hora", "C. C. Londres",
				"Quatro Caminhos", "R. Nova S. Gens", "António Pedro",
				"Sta. Apolonia", "Fogueteiros", "Padrão Légua",
				"Central Seixo", "Via Norte", "Picoutos", "Picoutos (viaduto)",
				"Pedra Verde", "Godinho Faria", "S. Mamede", "Laranjeiras",
				"Tv. Henrique Bravo", "Leonardo Coimbra", "Parada",
				"Rod. Gonçalves Lage (este)", "Gago Coutinho", "Formigueiro",
				"António Santos", "R. Do Mosteiro", "Águas Santas", "Corga",
				"Lavadouros", "Alto Da Maia", "Av.lidador Da Maia",
				"Palmilheira", "Heróis De Angola", "Padre Américo", "Formiga",
				"Duarte Pacheco", "4 Caminhos", "Tapada Azevedo",
				"Flor Da Serra", "Alto Da Serra", "Ribeiro Cambado",
				"Fonte Da Senhora", "Gaspar Corte Real", "Vale Chãs",
				"25 De Abril", "Pav. Gimnodesport.", "Calvário",
				"Câmara De Valongo", "Valongo (centro)", "Padrão", "Presa",
				"Aguiar Nogueira", "Valongo (estação)" });

		STOPS_STCP.put("64",
				new String[] { "Valongo", "5 Outubro", "Calvário",
						"Pav. Gimnodesport.", "Lagueirões", "Fonte", "Lousas",
						"Gandra Paciência", "Calfaiona", "António Sérgio",
						"Quinta Lousa", "Estação Serviço", "31 Janeiro",
						"Cabêda", "Viela S.vicente", "Oliveiras",
						"Aldeia Nova", "Barreira", "Igreja De Alfena",
						"Tv. Da Costa", "Alfena", "Alex. Herculano",
						"Codiceira", "Ferraria", "Ribeiro (alfena)" });

		STOPS_STCP.put("68", new String[] { "Hosp. S. João (metro)", "Asprela",
				"Enxurreiras", "Areosa (mercado)", "Areosa (feira)", "Triana",
				"Giesta", "Crespo", "Forno", "Tv. Brás Oleiro", "Tv. Forno",
				"Santegãos", "Forças Armadas", "Padre Ivo Tonelli", "Sra.lapa",
				"Boafé", "R. Casal", "R. Nova Sistelo", "Tv.castanheira",
				"Sistelo", "Bazar", "Rio Tinto (estação)", "Joaquim Neves",
				"Campainha", "Novo Cemitério", "Paço", "Quinta Do Paço",
				"Baguim", "Estrela", "Campinho", "Vale Ferreiros", "Pontelhas",
				"Carreira", "Ferraria", "Venda Nova", "Noé", "Felga",
				"Outeiro", "Lavadouros", "Montezelo", "Fanzeres (igreja)",
				"Costa", "Agras", "Álvarinho", "S.ta Cruz", "S.ta Eulália",
				"Capitão Moura", "Vilar", "Taralhão", "Vinhal",
				"Igreja Capuchinhos", "25 De Abril", "Combatentes G. Guerra",
				"Gondomar (câmara)", "B. V. Gondomar", "Gondomar" });

		STOPS_STCP.put("69", new String[] { "Bolhão-mercado", "S.ta Catarina",
				"Largo Do Padrão", "Campo 24 De Agosto", "Bonfim", "Godim",
				"Pr. Das Flores", "Av. 25 De Abril", "Fonte Velha",
				"Estádio Do Dragão", "Corujeira", "Ferr. Dos Santos", "Falcão",
				"Cartes", "Escolas", "S. Roque", "Tv. Da Ponte", "S. Caetano",
				"Sr. Do Calvário", "Chão Verde", "Cavada Nova", "S. Sebastião",
				"Venda Nova", "Noé", "Felga", "Outeiro", "Lavadouros",
				"Montezelo", "Fanzeres (igreja)", "Costa", "Agras",
				"Álvarinho", "S.ta Cruz", "S.ta Eulália", "Estivada",
				"Barreiros", "Sta.barbara", "Fanzeres (escola)",
				"Tv.bela Vista", "Alto Barreiros", "Montalto", "Vieira Sá",
				"Calvário", "Vanzeleres", "Escola Paço", "Centro Saúde",
				"Tv. Felga", "Sista", "Escola Primária", "Alto Regadas",
				"Seixo" });

		STOPS_STCP.put("70", new String[] { "Bolhão-mercado", "S.ta Catarina",
				"Largo Do Padrão", "Campo 24 De Agosto", "Bonfim", "Godim",
				"Pr. Das Flores", "Av. 25 De Abril", "Fonte Velha",
				"Estádio Do Dragão", "Corujeira", "Ferr. Dos Santos", "Falcão",
				"Cartes", "Escolas", "S. Roque", "Tv. Da Ponte", "S. Caetano",
				"Sr. Do Calvário", "Chão Verde", "Cavada Nova", "S. Sebastião",
				"Venda Nova", "Ferraria", "Carreira", "Pontelhas",
				"Vale Ferreiros", "Campinho", "Estrela", "Baguim", "Crasto",
				"Portocarro", "Presa Cano", "Paraíso", "Adão E Eva",
				"Auto Estrada", "Seminário Bom Pastor", "Formiga",
				"Padre Américo", "Norton Matos", "Bom Pastor",
				"Alberto Taborda", "Cruzeiro", "Ermesinde",
				"Ermesinde (estação)" });

		STOPS_STCP.put("94", new String[] { "Bolhão-mercado", "S.ta Catarina",
				"Largo Do Padrão", "Campo 24 De Agosto", "Bonfim", "Godim",
				"Pr. Das Flores", "Av. 25 De Abril", "Fonte Velha",
				"Estádio Do Dragão", "Corujeira", "Ferr. Dos Santos", "Falcão",
				"Cartes", "Escolas", "S. Roque", "Tv. Da Ponte", "S. Caetano",
				"Sr. Do Calvário", "Chão Verde", "Cavada Nova", "S. Sebastião",
				"Venda Nova", "Ferraria", "Carreira", "Pontelhas",
				"Vale Ferreiros", "Entre Cancelas", "Regadas", "Seixo",
				"Agostinho A. Sousa", "Monte Pedro", "Estr. D. Miguel",
				"Alto Da Serra", "Ribeiro Cambado", "Fonte Da Senhora",
				"Gaspar Corte Real", "Vale Chãs", "25 De Abril",
				"Pav. Gimnodesport.", "Calvário", "Câmara De Valongo",
				"Valongo (centro)", "Padrão", "Presa", "Aguiar Nogueira",
				"Valongo (estação)", "Águas Ferreas", "Chã", "Padre Américo",
				"Ponte Seca", "Campo" });

		STOPS_STCP.put("200", new String[] { "Bolhão", "Mercado Do Bolhão",
				"Pr. D. João I", "Guil. G. Fernandes", "Carmo",
				"Hosp. Sto. António", "Palácio", "Pr. Da Galiza",
				"Junta Massarelos", "Planetário", "Casa Das Artes",
				"Jardim Botânico", "Lordelo", "Palmeiras", "Fluvial (norte)",
				"Paulo Da Gama", "Pasteleira", "Torres", "Padre Luis Cabral",
				"Univ. Católica", "Pr. Do Império", "Mercado Da Foz",
				"Pr. De Liège", "Igreja Carmelitas", "Crasto", "Molhe",
				"Funchal", "Pêro Da Covilhã", "Timor", "Castelo Do Queijo" });

		STOPS_STCP.put("201", new String[] { "Sá Da Bandeira", "Av. Aliados",
				"Guil. G. Fernandes", "Carmo", "Hosp. Sto. António", "Palácio",
				"Pr. Da Galiza", "Boavista - B.sucesso",
				"Boavista-Casa Da Música", "Agramonte", "Guerra Junqueiro",
				"António Cardoso", "Bessa", "Foco", "Pinheiro Manso",
				"Gomes Da Costa", "Paulo Novais", "Fonte Da Moura",
				"Bairro Vilarinha", "Lidador", "Pereiró", "Ezequiel Campos",
				"Man. P. Azevedo 3", "Imtt", "Viso (metro)", "Viso" });

		STOPS_STCP.put("202", new String[] { "Lóios", "Av. Aliados",
				"Trindade", "Gonçalo Cristóvão", "Pr. Da República",
				"Figueiroa", "Carvalhosa", "Hospital Militar",
				"Boavista Brasília", "Boavista-Casa Da Música", "Graciosa",
				"Casa De Saúde Da Boavista", "Sidónio Pais", "Av. Do Bessa",
				"S.joão Bosco", "S. João De Brito", "Alberto Macedo",
				"Bairro Da Previdência", "Bairro Da Vilarinha",
				"Fonte Da Moura", "Av.da Boavista", "Liceu Garcia Orta",
				"R. Crasto", "Lgo.nevogilde", "Nevogilde", "Marechal Saldanha",
				"Funchal", "Pêro Da Covilhã", "Timor", "Castelo Do Queijo" });

		STOPS_STCP.put("203", new String[] { "Marquês", "Faria Guimarães",
				"Antero De Quental", "Piscina Constit.", "Sapador. Bombeiros",
				"Bcg", "Serpa Pinto", "Ramada Alta", "Cruz Vermelha",
				"Boavista-Casa Da Música", "Agramonte", "Guerra Junqueiro",
				"António Cardoso", "Bessa", "Foco", "Pinheiro Manso",
				"Gomes Costa 1", "Serralves", "Afonso Albuquerque",
				"Serralves (museu)", "João Barros", "Segurança Social",
				"Pr. Do Império", "Mercado Da Foz", "Pr. De Liège",
				"Igreja Carmelitas", "Crasto", "Molhe", "Funchal",
				"Pêro Da Covilhã", "Timor", "Castelo Do Queijo" });

		STOPS_STCP.put("204", new String[] { "Hosp. S. João (circunvalação)",
				"Hosp. S. João (urgência)", "Esc. Sup. Educação",
				"Faculdade De Engenharia", "Faculdade De Economia",
				"Manuel Laranjeira", "Outeiro", "Salgueiros", "Augusto Lessa",
				"Aval Cima", "Bento Júnior", "Covelo", "Campo Lindo",
				"Vale Formoso", "Silva Porto", "Bica Velha", "Natária",
				"Carvalhido", "Casa De Saúde Da Boavista", "Moreira Sá",
				"Casa Da Música (metro)", "Boavista-casa Da Música",
				"Bom Sucesso", "Pr. Da Galiza", "Junta Massarelos",
				"Planetário", "Casa Das Artes", "Jardim Botânico", "Lordelo",
				"Palmeiras", "Fluvial (norte)", "Paulo Da Gama", "Pasteleira",
				"Torres", "Padre Luis Cabral", "Univ. Católica",
				"Pr. Do Império", "Mercado Da Foz", "Foz" });

		STOPS_STCP.put("205",
				new String[] { "Campanhã", "Noeda", "Edp", "Freixo", "Bonjóia",
						"Lagarteiro", "Br.cerco Porto", "Pego Negro",
						"Bernardim Ribeiro", "S. Roque", "Fábrica Do Cobre",
						"Vila Cova", "Parque Nascente", "Ranha", "Rebordãos",
						"Varziela", "Rot. Da Areosa", "Areosa", "Enxurreiras",
						"Asprela", "Hosp. S. João (circunvalação)",
						"Ipo (circunval.)", "S. Tomé", "Amial", "Capuchinhos",
						"Via Norte (circ.)", "Mte Burgos (circ.)", "Congostas",
						"Br. Sto. Eugénio", "Quartel Militar", "Alto Do Viso",
						"S.ra Penha", "Ruela", "Rot. A.e.p.", "Preciosa",
						"Azenha De Cima", "Lidador /hospital",
						"Quinta Ingleses", "Real", "Teatro Vilarinha",
						"Parque Da Cidade", "D.afonso Henriques",
						"D. Afonso Cordeiro", "Pr. Cid. Salvador",
						"Edif. Transparente", "Castelo Do Queijo" });

		STOPS_STCP.put("206", new String[] { "Campanhã", "Piscina Campanhã",
				"Bonfim", "Campo 24 De Agosto", "D. João Iv", "Rua Firmeza",
				"Moreira", "Escola Normal", "Sto. Isidro",
				"Esc. Aurélia Sousa", "Marquês", "Faria Guimarães",
				"Constituição", "Vale Formoso", "Silva Porto", "Bica Velha",
				"Natária", "Carvalhido", "Castelos", "Maria Lamas", "Prelada",
				"Urb. Prelada", "Ramalde Do Meio", "Julgado De Paz", "Viso",
				"Viso (metro)", "Jerónimo Azevedo", "Bairro Do Viso",
				"R. Do Senhor", "Alto Do Viso", "Quartel Militar", "Cotovias",
				"Br. Sto. Eugénio (escola)" });

		STOPS_STCP.put("207", new String[] { "Campanhã", "Pinto Bessa",
				"Capela S.ra Saúde", "Heroismo", "Prado Repouso", "S. Lázaro",
				"Duque Loulé", "Alex. Herculano", "Batalha", "Pr. D. João I",
				"Guil. G. Fernandes", "Carmo", "Hosp. Sto. António", "Palácio",
				"Pr. Da Galiza", "Junta Massarelos", "Planetário",
				"Casa Das Artes", "Jardim Botânico", "Lordelo", "Aleixo",
				"S.ta Catarina", "Fluvial", "Paulo Da Gama",
				"Pousada Juventude", "Pasteleira", "Campo Pasteleira",
				"Igreja N. Sra Da Ajuda", "Br. Da Pasteleira",
				"Serralves (museu)", "Afonso Albuquerque", "João Barros",
				"Segurança Social", "Pr. Do Império", "Mercado Da Foz" });

		STOPS_STCP.put("208", new String[] { "Sá Bandeira", "Av. Aliados",
				"Guil. G. Fernandes", "Carmo", "Hosp. Sto. António", "Palácio",
				"Pr. Da Galiza", "Boavista - B.sucesso",
				"Boavista-Casa Da Música", "Graciosa",
				"Casa De Saúde Da Boavista", "Sidónio Pais", "Av. Do Bessa",
				"S. João Bosco", "S. João De Brito", "Igreja De Ramalde",
				"Casa De Ramalde", "Br. Ramalde", "C. Saúde Ramalde",
				"Br. Campinas", "Antunes Guimarães", "Lidador",
				"Br. Fonte Da Moura", "Pedra Verde", "Lidador-soeiro Mendes",
				"C.saúde De Aldoar", "Martim Freitas", "Vila Nova",
				"Alcaide Faria", "Aldoar Junta Freg." });

		SERVICES.put("Metro do Porto", STOPS_MPORTO);
		SERVICES.put("STCP", STOPS_STCP);
	}

}
