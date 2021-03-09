package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.util.List;
public class DataBase {
    private static DataBase singleInstance;
    static Configuration configuration = new Configuration().addAnnotatedClass(FlightsData.class).configure();
    static SessionFactory sf = configuration.buildSessionFactory();
    static Session session = sf.openSession();
    public static DataBase getInstance()
    {
        if(singleInstance==null)
        {
            singleInstance=new DataBase();
        }
        return singleInstance;
    }
    public static void insertIntoDB(FlightsData obj)
    {
        session.beginTransaction();
        session.save(obj);
        session.getTransaction().commit();
    }
    public static List<FlightsData> getFlightAccordingToUSer(InputData fsi) {
        session.getTransaction().begin();

        String orderby = fsi.getOutputPreference().equalsIgnoreCase("F") ? "FLIGHT_FARE" : "FLIGHT_FARE, FLIGHT_DUR";

        Query query = session.createQuery("select fd from FlightsData fd where SEAT_AVAIL = 'Y' and "
                + "FLIGHT_CLASS like '%" + fsi.getFlightClass() + "%' and " + "DEP_LOC = :depLoc and "
                + "ARR_LOC = :arrLoc and "
                + "STR_TO_DATE(VALID_TILL,'%d-%m-%Y')>= STR_TO_DATE( :date ,'%d-%m-%Y') " + "order by " + orderby);
        query.setParameter("depLoc", fsi.getDepartureLocation());
        query.setParameter("arrLoc", fsi.getArrivalLocation());
        query.setParameter("date", fsi.getFlightDate());
        @SuppressWarnings("unchecked")
        List<FlightsData> result = query.list();
        session.getTransaction().commit();
        return result;
    }
}
