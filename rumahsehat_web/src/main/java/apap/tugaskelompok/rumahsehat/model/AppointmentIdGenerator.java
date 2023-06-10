package apap.tugaskelompok.rumahsehat.model;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AppointmentIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

        String prefix = "APT-";
        String suffix = "";
        Connection connection = session.connection();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT COUNT(KODE) AS ID FROM APPOINTMENT");
            if(rs.next()) {
                Integer id = rs.getInt(1)+1;
                suffix = id.toString();
                String generatedId = prefix + suffix;
                return generatedId;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
