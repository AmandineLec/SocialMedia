package validationacquis;
import validationacquis.utils.*;
import java.sql.*;

public class Post {
    //#region Variables
    protected int id; 
    protected String title; 
    protected String description; 
    protected int like; 
    //#endregion

    //#region Constructeur 
    public Post(int id){
        try {
            ResultSet resultat = DBManager.query("SELECT * FROM Post WHERE id_post = "+id);
            if(resultat.next()){
                this.title = resultat.getString("titre");
                this.description = resultat.getString("description");
                this.id = id;
                this.like = this.nombrelike();
                }
            }
        catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public Post(String title, String description){
        this.title = title; 
        this.description = description; 
    }
    //#endregion

    //#region GETTER & SETTER
    public int getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public String getTitle() {
        return title;
    }
    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    //#endregion

    //#region METHOD

    public String toString(){
        return "Titre du poste : " + title + ". Ce poste a récolté " + like + " Likes"; 
    }

    public int nombrelike(){
        int id = this.getId();
        //POur connaitre les like d'un post, on a besoin de savoir : son id ? requête sql sur la table like et on fait un count en fonctionde l'id
        try {
            ResultSet resultat = DBManager.query("SELECT COUNT(*) as Likes FROM TableLike" +
                                                " INNER JOIN Post"+
                                                " ON TableLike.Post_id = Post.id_post" +
                                                " AND Post.id_post = "+id);
            if(resultat.next()){
                this.setLike(resultat.getInt("Likes"));
                }
            }
        catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return this.getLike();
    }
    public boolean save(int iduser){
        try {
            PreparedStatement stmt = DBManager.conn.prepareStatement("INSERT INTO Post(titre, description, User_id)"+
                                                                        " VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setInt(3, iduser);            
            stmt.execute();
            ResultSet resultat = stmt.getGeneratedKeys();
            if (resultat.next())
                this.id = resultat.getInt(1);
            return true;

        }catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }
    }
    //#endregion
}
