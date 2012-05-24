package com.cloud2bubble.ptsense;

import java.util.HashMap;
import java.util.Map;

public class PTService {

	public static Map<String, Map<String, String[]>> SERVICES = new HashMap<String, Map<String, String[]>>();

	static {
		Map<String, String[]> STOPS_MPORTO = new HashMap<String, String[]>();
		STOPS_MPORTO.put("A", new String[] { "Est�dio do Drag�o", "Campanh�",
				"Hero�smo", "Campo 24 de Agosto", "Bolh�o", "Trindade", "Lapa",
				"Carolina Michaelis", "Casa da M�sica", "Francos", "Ramalde",
				"Viso", "Sete Bicas", "Senhora da Hora", "Vasco da Gama",
				"Est�dio do Mar", "Pedro Hispano", "Parque Real",
				"C�mara de Matosinhos", "Matosinhos Sul", "Brito Capelo",
				"Mercado", "Senhor de Matosinhos" });

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

		STOPS_STCP.put("61", new String[] { "Matosinhos Mercado", "S. Pedro",
				"Lota", "Godinho", "Matosinhos Praia", "Afonso Cordeiro",
				"Afonso Henriques", "Matosinhos (cmm)", "Goa",
				"Hospital Matosinhos", "Seara", "Sendim (cemit�rio)",
				"Sarilho", "R.lagoa", "C. Saude Da Sra. Hora", "C. C. Londres",
				"Quatro Caminhos", "R. Nova S. Gens", "Ant�nio Pedro",
				"Sta. Apolonia", "Fogueteiros", "Padr�o L�gua",
				"Central Seixo", "Via Norte", "Picoutos", "Picoutos (viaduto)",
				"Pedra Verde", "Godinho Faria", "S. Mamede", "Laranjeiras",
				"Tv. Henrique Bravo", "Leonardo Coimbra", "Parada",
				"Rod. Gon�alves Lage (este)", "Gago Coutinho", "Formigueiro",
				"Ant�nio Santos", "R. Do Mosteiro", "�guas Santas", "Corga",
				"Lavadouros", "Alto Da Maia", "Av.lidador Da Maia",
				"Palmilheira", "Her�is De Angola", "Padre Am�rico", "Formiga",
				"Duarte Pacheco", "4 Caminhos", "Tapada Azevedo",
				"Flor Da Serra", "Alto Da Serra", "Ribeiro Cambado",
				"Fonte Da Senhora", "Gaspar Corte Real", "Vale Ch�s",
				"25 De Abril", "Pav. Gimnodesport.", "Calv�rio",
				"C�mara De Valongo", "Valongo (centro)", "Padr�o", "Presa",
				"Aguiar Nogueira", "Valongo (esta��o)" });

		STOPS_STCP.put("64",
				new String[] { "Valongo", "5 Outubro", "Calv�rio",
						"Pav. Gimnodesport.", "Lagueir�es", "Fonte", "Lousas",
						"Gandra Paci�ncia", "Calfaiona", "Ant�nio S�rgio",
						"Quinta Lousa", "Esta��o Servi�o", "31 Janeiro",
						"Cab�da", "Viela S.vicente", "Oliveiras",
						"Aldeia Nova", "Barreira", "Igreja De Alfena",
						"Tv. Da Costa", "Alfena", "Alex. Herculano",
						"Codiceira", "Ferraria", "Ribeiro (alfena)" });

		STOPS_STCP.put("68", new String[] { "Hosp. S. Jo�o (metro)", "Asprela",
				"Enxurreiras", "Areosa (mercado)", "Areosa (feira)", "Triana",
				"Giesta", "Crespo", "Forno", "Tv. Br�s Oleiro", "Tv. Forno",
				"Santeg�os", "For�as Armadas", "Padre Ivo Tonelli", "Sra.lapa",
				"Boaf�", "R. Casal", "R. Nova Sistelo", "Tv.castanheira",
				"Sistelo", "Bazar", "Rio Tinto (esta��o)", "Joaquim Neves",
				"Campainha", "Novo Cemit�rio", "Pa�o", "Quinta Do Pa�o",
				"Baguim", "Estrela", "Campinho", "Vale Ferreiros", "Pontelhas",
				"Carreira", "Ferraria", "Venda Nova", "No�", "Felga",
				"Outeiro", "Lavadouros", "Montezelo", "Fanzeres (igreja)",
				"Costa", "Agras", "�lvarinho", "S.ta Cruz", "S.ta Eul�lia",
				"Capit�o Moura", "Vilar", "Taralh�o", "Vinhal",
				"Igreja Capuchinhos", "25 De Abril", "Combatentes G. Guerra",
				"Gondomar (c�mara)", "B. V. Gondomar", "Gondomar" });

		STOPS_STCP.put("69", new String[] { "Bolh�o-mercado", "S.ta Catarina",
				"Largo Do Padr�o", "Campo 24 De Agosto", "Bonfim", "Godim",
				"Pr. Das Flores", "Av. 25 De Abril", "Fonte Velha",
				"Est�dio Do Drag�o", "Corujeira", "Ferr. Dos Santos", "Falc�o",
				"Cartes", "Escolas", "S. Roque", "Tv. Da Ponte", "S. Caetano",
				"Sr. Do Calv�rio", "Ch�o Verde", "Cavada Nova", "S. Sebasti�o",
				"Venda Nova", "No�", "Felga", "Outeiro", "Lavadouros",
				"Montezelo", "Fanzeres (igreja)", "Costa", "Agras",
				"�lvarinho", "S.ta Cruz", "S.ta Eul�lia", "Estivada",
				"Barreiros", "Sta.barbara", "Fanzeres (escola)",
				"Tv.bela Vista", "Alto Barreiros", "Montalto", "Vieira S�",
				"Calv�rio", "Vanzeleres", "Escola Pa�o", "Centro Sa�de",
				"Tv. Felga", "Sista", "Escola Prim�ria", "Alto Regadas",
				"Seixo" });

		STOPS_STCP.put("70", new String[] { "Bolh�o-mercado", "S.ta Catarina",
				"Largo Do Padr�o", "Campo 24 De Agosto", "Bonfim", "Godim",
				"Pr. Das Flores", "Av. 25 De Abril", "Fonte Velha",
				"Est�dio Do Drag�o", "Corujeira", "Ferr. Dos Santos", "Falc�o",
				"Cartes", "Escolas", "S. Roque", "Tv. Da Ponte", "S. Caetano",
				"Sr. Do Calv�rio", "Ch�o Verde", "Cavada Nova", "S. Sebasti�o",
				"Venda Nova", "Ferraria", "Carreira", "Pontelhas",
				"Vale Ferreiros", "Campinho", "Estrela", "Baguim", "Crasto",
				"Portocarro", "Presa Cano", "Para�so", "Ad�o E Eva",
				"Auto Estrada", "Semin�rio Bom Pastor", "Formiga",
				"Padre Am�rico", "Norton Matos", "Bom Pastor",
				"Alberto Taborda", "Cruzeiro", "Ermesinde",
				"Ermesinde (esta��o)" });

		STOPS_STCP.put("94", new String[] { "Bolh�o-mercado", "S.ta Catarina",
				"Largo Do Padr�o", "Campo 24 De Agosto", "Bonfim", "Godim",
				"Pr. Das Flores", "Av. 25 De Abril", "Fonte Velha",
				"Est�dio Do Drag�o", "Corujeira", "Ferr. Dos Santos", "Falc�o",
				"Cartes", "Escolas", "S. Roque", "Tv. Da Ponte", "S. Caetano",
				"Sr. Do Calv�rio", "Ch�o Verde", "Cavada Nova", "S. Sebasti�o",
				"Venda Nova", "Ferraria", "Carreira", "Pontelhas",
				"Vale Ferreiros", "Entre Cancelas", "Regadas", "Seixo",
				"Agostinho A. Sousa", "Monte Pedro", "Estr. D. Miguel",
				"Alto Da Serra", "Ribeiro Cambado", "Fonte Da Senhora",
				"Gaspar Corte Real", "Vale Ch�s", "25 De Abril",
				"Pav. Gimnodesport.", "Calv�rio", "C�mara De Valongo",
				"Valongo (centro)", "Padr�o", "Presa", "Aguiar Nogueira",
				"Valongo (esta��o)", "�guas Ferreas", "Ch�", "Padre Am�rico",
				"Ponte Seca", "Campo" });

		STOPS_STCP.put("200", new String[] { "Bolh�o", "Mercado Do Bolh�o",
				"Pr. D. Jo�o I", "Guil. G. Fernandes", "Carmo",
				"Hosp. Sto. Ant�nio", "Pal�cio", "Pr. Da Galiza",
				"Junta Massarelos", "Planet�rio", "Casa Das Artes",
				"Jardim Bot�nico", "Lordelo", "Palmeiras", "Fluvial (norte)",
				"Paulo Da Gama", "Pasteleira", "Torres", "Padre Luis Cabral",
				"Univ. Cat�lica", "Pr. Do Imp�rio", "Mercado Da Foz",
				"Pr. De Li�ge", "Igreja Carmelitas", "Crasto", "Molhe",
				"Funchal", "P�ro Da Covilh�", "Timor", "Castelo Do Queijo" });

		STOPS_STCP.put("201", new String[] { "S� Da Bandeira", "Av. Aliados",
				"Guil. G. Fernandes", "Carmo", "Hosp. Sto. Ant�nio", "Pal�cio",
				"Pr. Da Galiza", "Boavista - B.sucesso",
				"Boavista-Casa Da M�sica", "Agramonte", "Guerra Junqueiro",
				"Ant�nio Cardoso", "Bessa", "Foco", "Pinheiro Manso",
				"Gomes Da Costa", "Paulo Novais", "Fonte Da Moura",
				"Bairro Vilarinha", "Lidador", "Pereir�", "Ezequiel Campos",
				"Man. P. Azevedo 3", "Imtt", "Viso (metro)", "Viso" });

		STOPS_STCP.put("202", new String[] { "L�ios", "Av. Aliados",
				"Trindade", "Gon�alo Crist�v�o", "Pr. Da Rep�blica",
				"Figueiroa", "Carvalhosa", "Hospital Militar",
				"Boavista Bras�lia", "Boavista-Casa Da M�sica", "Graciosa",
				"Casa De Sa�de Da Boavista", "Sid�nio Pais", "Av. Do Bessa",
				"S.jo�o Bosco", "S. Jo�o De Brito", "Alberto Macedo",
				"Bairro Da Previd�ncia", "Bairro Da Vilarinha",
				"Fonte Da Moura", "Av.da Boavista", "Liceu Garcia Orta",
				"R. Crasto", "Lgo.nevogilde", "Nevogilde", "Marechal Saldanha",
				"Funchal", "P�ro Da Covilh�", "Timor", "Castelo Do Queijo" });

		STOPS_STCP.put("203", new String[] { "Marqu�s", "Faria Guimar�es",
				"Antero De Quental", "Piscina Constit.", "Sapador. Bombeiros",
				"Bcg", "Serpa Pinto", "Ramada Alta", "Cruz Vermelha",
				"Boavista-Casa Da M�sica", "Agramonte", "Guerra Junqueiro",
				"Ant�nio Cardoso", "Bessa", "Foco", "Pinheiro Manso",
				"Gomes Costa 1", "Serralves", "Afonso Albuquerque",
				"Serralves (museu)", "Jo�o Barros", "Seguran�a Social",
				"Pr. Do Imp�rio", "Mercado Da Foz", "Pr. De Li�ge",
				"Igreja Carmelitas", "Crasto", "Molhe", "Funchal",
				"P�ro Da Covilh�", "Timor", "Castelo Do Queijo" });

		STOPS_STCP.put("204", new String[] { "Hosp. S. Jo�o (circunvala��o)",
				"Hosp. S. Jo�o (urg�ncia)", "Esc. Sup. Educa��o",
				"Faculdade De Engenharia", "Faculdade De Economia",
				"Manuel Laranjeira", "Outeiro", "Salgueiros", "Augusto Lessa",
				"Aval Cima", "Bento J�nior", "Covelo", "Campo Lindo",
				"Vale Formoso", "Silva Porto", "Bica Velha", "Nat�ria",
				"Carvalhido", "Casa De Sa�de Da Boavista", "Moreira S�",
				"Casa Da M�sica (metro)", "Boavista-casa Da M�sica",
				"Bom Sucesso", "Pr. Da Galiza", "Junta Massarelos",
				"Planet�rio", "Casa Das Artes", "Jardim Bot�nico", "Lordelo",
				"Palmeiras", "Fluvial (norte)", "Paulo Da Gama", "Pasteleira",
				"Torres", "Padre Luis Cabral", "Univ. Cat�lica",
				"Pr. Do Imp�rio", "Mercado Da Foz", "Foz" });

		STOPS_STCP.put("205",
				new String[] { "Campanh�", "Noeda", "Edp", "Freixo", "Bonj�ia",
						"Lagarteiro", "Br.cerco Porto", "Pego Negro",
						"Bernardim Ribeiro", "S. Roque", "F�brica Do Cobre",
						"Vila Cova", "Parque Nascente", "Ranha", "Rebord�os",
						"Varziela", "Rot. Da Areosa", "Areosa", "Enxurreiras",
						"Asprela", "Hosp. S. Jo�o (circunvala��o)",
						"Ipo (circunval.)", "S. Tom�", "Amial", "Capuchinhos",
						"Via Norte (circ.)", "Mte Burgos (circ.)", "Congostas",
						"Br. Sto. Eug�nio", "Quartel Militar", "Alto Do Viso",
						"S.ra Penha", "Ruela", "Rot. A.e.p.", "Preciosa",
						"Azenha De Cima", "Lidador /hospital",
						"Quinta Ingleses", "Real", "Teatro Vilarinha",
						"Parque Da Cidade", "D.afonso Henriques",
						"D. Afonso Cordeiro", "Pr. Cid. Salvador",
						"Edif. Transparente", "Castelo Do Queijo" });

		STOPS_STCP.put("206", new String[] { "Campanh�", "Piscina Campanh�",
				"Bonfim", "Campo 24 De Agosto", "D. Jo�o Iv", "Rua Firmeza",
				"Moreira", "Escola Normal", "Sto. Isidro",
				"Esc. Aur�lia Sousa", "Marqu�s", "Faria Guimar�es",
				"Constitui��o", "Vale Formoso", "Silva Porto", "Bica Velha",
				"Nat�ria", "Carvalhido", "Castelos", "Maria Lamas", "Prelada",
				"Urb. Prelada", "Ramalde Do Meio", "Julgado De Paz", "Viso",
				"Viso (metro)", "Jer�nimo Azevedo", "Bairro Do Viso",
				"R. Do Senhor", "Alto Do Viso", "Quartel Militar", "Cotovias",
				"Br. Sto. Eug�nio (escola)" });

		STOPS_STCP.put("207", new String[] { "Campanh�", "Pinto Bessa",
				"Capela S.ra Sa�de", "Heroismo", "Prado Repouso", "S. L�zaro",
				"Duque Loul�", "Alex. Herculano", "Batalha", "Pr. D. Jo�o I",
				"Guil. G. Fernandes", "Carmo", "Hosp. Sto. Ant�nio", "Pal�cio",
				"Pr. Da Galiza", "Junta Massarelos", "Planet�rio",
				"Casa Das Artes", "Jardim Bot�nico", "Lordelo", "Aleixo",
				"S.ta Catarina", "Fluvial", "Paulo Da Gama",
				"Pousada Juventude", "Pasteleira", "Campo Pasteleira",
				"Igreja N. Sra Da Ajuda", "Br. Da Pasteleira",
				"Serralves (museu)", "Afonso Albuquerque", "Jo�o Barros",
				"Seguran�a Social", "Pr. Do Imp�rio", "Mercado Da Foz" });

		STOPS_STCP.put("208", new String[] { "S� Bandeira", "Av. Aliados",
				"Guil. G. Fernandes", "Carmo", "Hosp. Sto. Ant�nio", "Pal�cio",
				"Pr. Da Galiza", "Boavista - B.sucesso",
				"Boavista-Casa Da M�sica", "Graciosa",
				"Casa De Sa�de Da Boavista", "Sid�nio Pais", "Av. Do Bessa",
				"S. Jo�o Bosco", "S. Jo�o De Brito", "Igreja De Ramalde",
				"Casa De Ramalde", "Br. Ramalde", "C. Sa�de Ramalde",
				"Br. Campinas", "Antunes Guimar�es", "Lidador",
				"Br. Fonte Da Moura", "Pedra Verde", "Lidador-soeiro Mendes",
				"C.sa�de De Aldoar", "Martim Freitas", "Vila Nova",
				"Alcaide Faria", "Aldoar Junta Freg." });

		SERVICES.put("Metro do Porto", STOPS_MPORTO);
		SERVICES.put("STCP", STOPS_STCP);
	}

}
