package com.org.acs.gr.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import com.org.acs.gr.bean.CsvGameBean;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CsvService {
	private final List<String> HEADERS = new ArrayList<>() {
		private static final long serialVersionUID = 2617271888032890547L;
		{
			add("name");
			add("description");

		}
	};

	public void exportCsv(List<CsvGameBean> games, List<String> genres) throws IOException {
		List<String> headers = new ArrayList<>();
		headers.addAll(HEADERS);
		headers.addAll(genres);

		FileWriter out = new FileWriter("book_new.csv");
		try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(headers.toArray(String[]::new)))) {
			for (CsvGameBean game : games) {
				List<String> rowData = new ArrayList<>();
				rowData.add(game.getName());
				rowData.add(game.getDescription());
				rowData.addAll(game.getGenres());
				printer.printRecord(rowData);
			}
		}
	}
}
