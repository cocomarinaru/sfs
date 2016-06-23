package km.barsim.db;

import java.sql.*;

/**
 * Created by ovidiu on 23.06.2016.
 */
public class UserService {

    private UserService instance;

    public UserService getInstance(){

        if (instance == null){
            instance = new UserService();
        }

        return instance;
    }

    private UserService(){

    }


    public void getUserRating(String userName){

        Connection connection = DatabaseConnector.getConnection();

        try {

            String sql = "select COUNT(*) as count from user_score_summary" ;
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            int count = rs.getInt("count");


        } catch (SQLException e) {

        }

    }


//
//    public function getTier()
//    {
//        // Connect to DB
//        $con = Propel::getServiceContainer()->getReadConnection(UserTableMap::DATABASE_NAME);
//
//        // Count number of users
//        $sql = "select COUNT(*) as count from user_score_summary";
//        $stmt = $con->prepare($sql);
//        $stmt->execute();
//        $row = $stmt->fetch(\PDO::FETCH_OBJ);
//        $count = $row->count;
//
//		/*
//		* $this->id e id-ul userului si se citeste din DB din tabela user
//
//		SELECT id FROM user WHERE username = 'user' LIMIT 1
//		*/
//
//        // Calculeaza un ranking direct in SQL
//        $sql = "
//        SELECT x.user_id, x.position, x.h2h_score
//        FROM (SELECT t.user_id, t.h2h_score, @rownum := @rownum + 1 AS position
//        FROM user_score_summary t
//        JOIN (SELECT @rownum := 0) r
//        ORDER BY t.h2h_score DESC) x
//        WHERE x.user_id = '" . $this->id ."'";
//
//        $stmt = $con->prepare($sql);
//        $stmt->execute();
//        $row = $stmt->fetch(\PDO::FETCH_OBJ);
//
//        // asta e rank-ul
//        var_dump ( $row->position );
//    }





}
