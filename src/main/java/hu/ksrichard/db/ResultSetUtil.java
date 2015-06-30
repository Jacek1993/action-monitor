package hu.ksrichard.db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for converting resultset to readable {@link String}
 * @author ksrichard
 */
public class ResultSetUtil {

    /**
     * Convert {@link java.sql.ResultSet} to {@link String}
     * @param resultSet ResultSet from database event
     * @return {@link String}
     * @throws SQLException
     */
    public static String toString(ResultSet resultSet) throws SQLException {
        StringBuilder sb = new StringBuilder();
        if(resultSet != null && resultSet.getMetaData().getColumnCount() > 0){
            for(int i=1;i<=resultSet.getMetaData().getColumnCount();i++){
                sb.append(resultSet.getMetaData().getColumnLabel(i)+" -> "
                        +resultSet.getObject(resultSet.getMetaData().getColumnName(i))+",");
            }
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }

}
