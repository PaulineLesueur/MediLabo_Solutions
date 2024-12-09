db = db.getDB("MediLaboSolutions");
const data = JSON.parse(cat('data.json'));

db.notes.insertMany(data);
