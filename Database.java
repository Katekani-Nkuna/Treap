@SuppressWarnings("unchecked")
public class Database {
    String[][] database;
    String[] columnNames;
    Treap<Cell>[] indexes;

    public Database(String[] cols, int maxSize) {
        database = new String[maxSize][];

        for (int i = 0; i < maxSize; i++){
            database[i] = new String[cols.length];
        }

        columnNames = new String[cols.length];

        for (int i = 0; i < cols.length; i++){
            columnNames[i] = cols[i];
        }

        indexes = new Treap[cols.length];

        for (int i = 0; i < indexes.length; i++){
            indexes[i] = null;
        }
    }

    public void insert(String[] newRowDetails) throws DatabaseException {
        if(newRowDetails.length != columnNames.length){
            throw DatabaseException.invalidNumberOfColums();
        }

        // get next available row number
        int rowNum = -1;
        for (int i = 0; i < database.length; i++){
            if (database[i][0] == null) {
                rowNum = i;
                break;
            }
        }

        //Check Duplicates on non null treaps
        for (int i = 0; i< newRowDetails.length; i++){
            Cell newCell = new Cell(rowNum, newRowDetails[i]);
            if(indexes[i] != null && indexes[i].access(newCell) != null){
                throw DatabaseException.duplicateInsert(newCell);
            }
        }

        //Check if database is full
        if(rowNum == -1){
            throw DatabaseException.databaseFull();
        }

        //All validations pass, insert new row into db and values into treaps
        database[rowNum] = newRowDetails;
        for (int i = 0; i < newRowDetails.length; i++){
            Cell newCell = new Cell(rowNum, newRowDetails[i]);

            if (indexes[i] != null){
                indexes[i].insert(newCell);
            }
        }
    }

    public String[] removeFirstWhere(String col, String data) throws DatabaseException {

        //Find col number for col
        int colNumber = findColNumber(col);
        if (colNumber == -1){
            throw DatabaseException.invalidColumnName(col);
        }

        //Get col index
        Treap<Cell> colIndex = indexes[colNumber];
        Node<Cell> cellData;
        int databaseRow = -1;
        if (colIndex != null)
        {
            cellData = colIndex.remove( new Cell(-1,data));

            if (cellData != null ){
                databaseRow = cellData.data.databaseRow;
            }
        }

        if (databaseRow == -1){
            for (int i = 0; i < database.length; i++){
                if (database[i][0] == null)
                    continue;

                if (database[i][colNumber].equals(data)){
                    databaseRow = i;
                    break;
                }
            }
        }

        if (databaseRow == -1){
            return new String[0];
        }

        String[] row = database[databaseRow];
        for (int i = 0; i < row.length; i++){
            colIndex = indexes[i];

            if (colIndex != null)
            {
                colIndex.remove( new Cell(-1,row[i]));
            }
        }
        database[databaseRow] = new String[columnNames.length];
        return row;
    }

    public String[][] removeAllWhere(String col, String data) throws DatabaseException {

        String [][] removedRows = new String[database.length][];
        String [] removedRow;
        int i = 0;
        while (true){
            removedRow = removeFirstWhere(col,data);
            if (removedRow.length == 0)
                break;

            removedRows[i++] = removedRow;
        }

        if (i == 0){
            return new String[0][0];
        }

        String[][] result = new String[i][columnNames.length];
        System.arraycopy(removedRows, 0, result, 0, i);

        return result;
    }

    public String[] findFirstWhere(String col, String data) throws DatabaseException {
        //Find col number for col
        int colNumber = findColNumber(col);
        if (colNumber == -1){
            throw DatabaseException.invalidColumnName(col);
        }

        //Get col index
        Treap<Cell> colIndex = indexes[colNumber];
        Node<Cell> cellData;
        int databaseRow = -1;
        if (colIndex != null)
        {
            cellData = colIndex.access( new Cell(-1,data));

            if (cellData != null ){
                databaseRow = cellData.data.databaseRow;
            }
        }

        if (databaseRow == -1){
            for (int i = 0; i < database.length; i++){
                if (database[i][0] == null) {
                    continue;
                }
                if (database[i][colNumber].equals(data)){
                    databaseRow = i;
                    break;
                }
            }
        }

        if (databaseRow == -1){
            return new String[0];
        }

        return database[databaseRow];
    }

    public String[][] findAllWhere(String col, String data) throws DatabaseException {

        //Find col number for col
        int colNumber = findColNumber(col);
        if (colNumber == -1){
            throw DatabaseException.invalidColumnName(col);
        }

        //Get col index
        Treap<Cell> colIndex = indexes[colNumber];
        Node<Cell> cellData;
        int databaseRow = -1;
        if (colIndex != null)
        {
            cellData = colIndex.access( new Cell(-1,data));

            if (cellData != null ){
                databaseRow = cellData.data.databaseRow;
                String [][] foundRow = new String[1][];
                foundRow[0] = database[databaseRow];
                return foundRow;

            }
        }

        String [][] foundRows = new String[database.length][];
        int foundRow = 0;
        for (String[] row : database) {
            if (row[0] == null)
                continue;

            if (row[colNumber].equals(data)) {
                foundRows[foundRow++] = row;
            }
        }

        String[][] result = new String[foundRow][columnNames.length];
        System.arraycopy(foundRows, 0, result, 0, foundRow);

        return result;
    }

    public String[] updateFirstWhere(String col, String updateCondition, String data) throws DatabaseException {
        //Find col number for col
        int colNumber = findColNumber(col);
        if (colNumber == -1){
            throw DatabaseException.invalidColumnName(col);
        }

        //Get col index
        Treap<Cell> colIndex = indexes[colNumber];
        Node<Cell> cellData;
        int databaseRow = -1;

        if (colIndex != null)
        {
            cellData = colIndex.remove( new Cell(-1,updateCondition));

            if (cellData != null ){
                databaseRow = cellData.data.databaseRow;
                colIndex.insert( new Cell(databaseRow, data));
            }
        }

        if (databaseRow == -1){
            for (int i = 0; i < database.length; i++){

                if (database[i][0] == null)
                    continue;

                if (database[i][colNumber].equals(updateCondition)){
                    databaseRow = i;
                    break;
                }
            }
        }

        if (databaseRow == -1){
            return new String[0];
        }

        database[databaseRow][colNumber] = data;
        return database[databaseRow];
    }

    public String[][] updateAllWhere(String col, String updateCondition, String data) throws DatabaseException {
        String [][] updatedRows = new String[database.length][];
        String [] updatedRow;
        int i = 0;
        while (true){
            updatedRow = updateFirstWhere(col,updateCondition, data);
            if (updatedRow.length == 0)
                break;

            updatedRows[i++] = updatedRow;
        }

        if (i == 0){
            return new String[0][0];
        }

        String[][] result = new String[i][columnNames.length];
        System.arraycopy(updatedRows, 0, result, 0, i);

        return result;
    }

    public Treap<Cell> generateIndexOn(String col) throws DatabaseException {

        //Find col number for col
        int colNumber = findColNumber(col);
        if (colNumber == -1){
            throw DatabaseException.invalidColumnName(col);
        }

        if (isEmpty() && indexes[colNumber] == null){
            return new Treap<Cell>();
        }

        //Get col index
        Treap<Cell> colIndex = indexes[colNumber];
        Node<Cell> cellData;
        int databaseRow = -1;
        if (colIndex != null)
        {
            return colIndex;
        }

        indexes[colNumber] = new Treap<Cell>();

        for (int i = 0; i < database.length; i++){

            if (database[i][0] == null)
                continue;

            try {
                indexes[colNumber].insert(new Cell(i, database[i][colNumber]));
            }catch (DatabaseException dbException){
                indexes[colNumber] = null;
                throw dbException;
            }

        }

        return indexes[colNumber];
    }

    public Treap<Cell>[] generateIndexAll() throws DatabaseException {
        for (int i = 0; i < columnNames.length; i++){
            Treap<Cell> colIndex;
            try {
                colIndex = generateIndexOn(columnNames[i]);
            }catch (DatabaseException dbException){
                continue;
            }

            indexes[i] = colIndex;
        }

        return indexes;
    }

    public int countOccurences(String col, String data) throws DatabaseException {
        //Find col number for col
        int colNumber = findColNumber(col);
        if (colNumber == -1){
            throw DatabaseException.invalidColumnName(col);
        }

        int count = 0;

        //Get col index
        Treap<Cell> colIndex = indexes[colNumber];
        Node<Cell> cellData;
        if (colIndex != null)
        {
            cellData = colIndex.access( new Cell(-1,data));

            if (cellData != null ){
                return 1;
            }
        }

        for (String[] row : database) {
            if (row[0] == null)
                continue;
            if (row[colNumber].equals(data)) {
                count++;
            }
        }

        return count;
    }

    boolean validateRowDetails(String[] newRowDetails){

        String value = newRowDetails[0];

        if (!isNumber(value)){
            return false;
        }

        value = newRowDetails[3];
        if (!isNumber(value)){
            return false;
        }

        return true;
    }

    boolean isNumber(String value){
        if (value == null) {
            return false;
        }
        try {
            double d = Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }

    int findColNumber(String col){
        for (int i = 0; i < columnNames.length; i++){
            if (col.equals(columnNames[i]))
                return i;
        }

        return -1;
    }

    boolean isEmpty(){
        for (String [] row : database){
            if (row != null){
                return false;
            }
        }

        return true;
    }
}
