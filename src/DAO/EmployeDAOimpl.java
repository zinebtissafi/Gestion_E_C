package DAO;
import Model.Employe;
import Model.Post;
import Model.Role;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class EmployeDAOimpl implements GenericDAOI<Employe>,DataImportExport<Employe> {

    @Override
    public void add(Employe e) {
        String sql = "INSERT INTO employe (nom, prenom, email, telephone, salaire, role, poste , solde) VALUES (?, ?, ?, ?, ?, ?, ? , ?)";
        try (PreparedStatement stmt = DBConnexion.getConnexion().prepareStatement(sql)) {
            stmt.setString(1, e.getNom()); 
            stmt.setString(2, e.getPrenom());
            stmt.setString(3, e.getEmail());
            stmt.setString(4, e.getTelephone());
            stmt.setDouble(5, e.getSalaire());
            stmt.setString(6, e.getRole().name());
            stmt.setString(7, e.getPost().name());
            stmt.setInt(8, e.getSolde());
            stmt.executeUpdate();
        } catch (SQLException exception) {
            System.err.println("failed of add employe ");
        } catch (ClassNotFoundException ex) {
            System.err.println("failed connexion with data base");
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM employe WHERE id = ?";
        try (PreparedStatement stmt = DBConnexion.getConnexion().prepareStatement(sql)) {
            stmt.setInt(1,id);
            stmt.executeUpdate();
        } catch (SQLException exception) {
            System.err.println("failed of delete employe");
        } catch (ClassNotFoundException ex) {
            System.err.println("failed connexion with data base");
        }
    }

    @Override
    public void update(Employe e) {
        String sql = "UPDATE employe SET nom = ?, prenom = ?, email = ?, telephone = ?, salaire = ?, role = ?, poste = ? WHERE id = ?";
        try (PreparedStatement stmt = DBConnexion.getConnexion().prepareStatement(sql)) {
            stmt.setString(1, e.getNom());
            stmt.setString(2, e.getPrenom());
            stmt.setString(3, e.getEmail());
            stmt.setString(4, e.getTelephone());
            stmt.setDouble(5, e.getSalaire());
            stmt.setString(6, e.getRole().name());
            stmt.setString(7, e.getPost().name());
            stmt.setInt(8,e.getId());
            stmt.executeUpdate();
        } catch (SQLException exception) {
            System.err.println("failed of update employe");
        } catch (ClassNotFoundException ex) {
            System.err.println("failed connexion with data base");
        }
    }

    @Override
    public List<Employe> display() {
        String sql = "SELECT * FROM employe";
        List<Employe> Employes = new ArrayList<>();
        try (PreparedStatement stmt = DBConnexion.getConnexion().prepareStatement(sql)) {
            ResultSet re = stmt.executeQuery();
            while (re.next()) {
                int id = re.getInt("id");
                String nom = re.getString("nom");
                String prenom = re.getString("prenom");
                String email = re.getString("email");
                String telephone = re.getString("telephone");
                double salaire = re.getDouble("salaire");
                String role = re.getString("role");
                String poste = re.getString("poste");
                int solde = re.getInt("solde");
                Employe e = new Employe(id,nom, prenom, email, telephone, salaire, Role.valueOf(role), Post.valueOf(poste),solde);
                Employes.add(e);
            }
            return Employes;
        } catch (ClassNotFoundException ex) {
            System.err.println("failed connexion with data base");
            return null;
        } catch (SQLException ex) {
            System.err.println("failed of display employe");
            return null;
        }
    }


    public void updateSolde(int id, int solde) {
        String sql = "UPDATE employe SET solde = ? WHERE id = ?";
        try (PreparedStatement stmt = DBConnexion.getConnexion().prepareStatement(sql)) {
            stmt.setInt(1, solde);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException exception) {
            System.err.println("failed of update solde employe");
        } catch (ClassNotFoundException ex) {
            System.err.println("failed connexion with data base");
        }
    }

    @Override
    public void importData(String filePath) {
        String query = "INSERT INTO Employe(nom, prenom, email, telephone, salaire, role, poste) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             PreparedStatement psmt = DBConnexion.getConnexion().prepareStatement(query)){

            String line = reader.readLine(); // Skip the header
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 7) {
                	psmt.setString(1, data[0].trim()); // nom
                	psmt.setString(2, data[1].trim()); // prenom
                	psmt.setString(3, data[2].trim()); // email
                	psmt.setString(4, data[3].trim()); // telephone
                	psmt.setString(5, data[4].trim()); // salaire
                	psmt.setString(6, data[5].trim()); // role
                	psmt.setString(7, data[6].trim()); // poste
                	psmt.addBatch();
                } else {
                    System.err.println("Invalid line format: " + line);
                }
            }
            psmt.executeBatch();
            System.out.println("Employés importés avec succès.");
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
	@Override
	public void exportData(String fileName, List<Employe> data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("nom,prenom,email,telephone,role,poste,salaire");
            writer.newLine();
            for (Employe employee : data) {
                String line = String.format("%s,%s,%s,%s,%s,%s,%.2f",
                        employee.getNom(),
                        employee.getPrenom(),
                        employee.getEmail(),
                        employee.getTelephone(),
                        employee.getRole(),
                        employee.getPost(),
                        employee.getSalaire());
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Données exportées avec succès.");
        }
    }
}

