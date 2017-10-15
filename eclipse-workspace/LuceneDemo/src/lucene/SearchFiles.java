/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lucene;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.search.similarities.BasicStats;
import org.apache.lucene.search.similarities.SimilarityBase;

public class Search {

	// private static final String INDEX_DIR =
	// "/Users/Nithin/Desktop/paragraphIndex";

	static final int i = 0;

	private Search() {
	}

	/** Simple command-line based search demo. */
	public static void main(String[] args) throws Exception {

		String TEST200_DIR = args[0];
		String INDEX_DIR = args[1];
		Indexer indexer = new Indexer(TEST200_DIR, INDEX_DIR);
		IndexSearcher searcher = createSearcher(INDEX_DIR);

		SimilarityBase mySimilarity = new SimilarityBase() {

			@Override
			public String toString() {
				// TODO Auto-generated method stub
				return "Frequency results based on sum #{q_i}";
			}

			@Override
			protected float score(BasicStats arg0, float arg1, float arg2) {
				// TODO Auto-generated method stub
				return arg0.getTotalTermFreq();
			}
		};

		searcher.setSimilarity(mySimilarity);
		

		/*
		 * Search by contents List of Queries to be searched
		 */
		TopDocs foundDocs1 = searchQuery("power nap benefits", searcher);
		TopDocs foundDocs2 = searchQuery("whale vocalization production of sound", searcher);
		TopDocs foundDocs3 = searchQuery("pokemon puzzle league", searcher);

		System.out.println("listing the paragraph IDs and contents\n");
		// Searching Query 1 - " power nap benefits"
		System.out.println("Query 1 :- power nap benefits");
		System.out.println("\nTotal Results :: " + foundDocs1.totalHits + "\n Content of the top 10 results");
		for (ScoreDoc sd : foundDocs1.scoreDocs) {
			Document d = searcher.doc(sd.doc);
			System.out.println(String.format(d.get("id")) + " - " + d.get("contents"));

		}

		// Searching Query 2 - " whale vocalization production of sound"
		System.out.println("\nQuery 2 :- whale vocalization production of sound");
		System.out.println("\nTotal Results :: " + foundDocs2.totalHits + "\n Content of the top 10 results");
		for (ScoreDoc sd : foundDocs2.scoreDocs) {
			Document d = searcher.doc(sd.doc);
			System.out.println(String.format(d.get("id")) + " - " + d.get("contents"));

		}

		// Searching Query 3 - " pokemon puzzle league"
		System.out.println("\nQuery 3 :- pokemon puzzle league");
		System.out.println("\nTotal Results :: " + foundDocs3.totalHits + "\n Content of the top 10 results");
		for (ScoreDoc sd : foundDocs3.scoreDocs) {
			Document d = searcher.doc(sd.doc);
			System.out.println(String.format(d.get("id")) + " - " + d.get("contents"));

		}

	}

	/*
	 * Argument 1 - Query String returns total number of results
	 */
	private static TopDocs searchQuery(String query, IndexSearcher searcher) throws Exception {
		QueryParser qp = new QueryParser("contents", new StandardAnalyzer());
		Query QueryString = qp.parse(query);
		TopDocs hits = searcher.search(QueryString, 10);
		return hits;
	}

	/*
	 * Arg 1 - relative path to the index directory
	 */
	private static IndexSearcher createSearcher(String INDEX_DIR) throws IOException {
		String index_dir = INDEX_DIR;
		Directory dir = FSDirectory.open(Paths.get(index_dir));
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		return searcher;
	}
}