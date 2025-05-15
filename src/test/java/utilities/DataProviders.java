package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	// DataProvide 1
	@DataProvider(name="LoginData")
	public String[][] getData() throws IOException {

		String path = "./testData/OpenCart_testdata.xlsx";

		ExcelUtils xlUtil = new ExcelUtils(path);

		int totalrows = xlUtil.getRowCount("sheet1");
		int totalcols = xlUtil.getCellCount("sheet1", 1);

		String loginData[][] = new String[totalrows][totalcols];

		for (int i = 1; i <= totalrows; i++) {

			for (int j = 0; j < totalcols; j++) {

				loginData[i-1][j] = xlUtil.getCellData("sheet1", i, j);
			}

		}

		return loginData;
	}

}
