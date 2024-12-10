print("Démarrage de l'initialisation de la base de données...");

db = new Mongo("mongodb://root:rootroot@mongodb:27017/MediLaboSolutions");
db.createCollection("notes");

const data = JSON.parse(cat('data.json'));
print("Insertion des données...");
db.notes.insertMany(data);

print("Insertion terminée.");
