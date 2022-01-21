import pickle
from flask import Flask, request

app = Flask(__name__)


def predict_genres(description):
    ml_model = pickle.load(open('trained_model', 'rb'))
    decoder = pickle.load(open('multilabel_decoder', 'rb'))
    vectorizer = pickle.load(open('tfidf_vectorizer', 'rb'))

    q_vec = vectorizer.transform([description])
    q_pred = ml_model.predict(q_vec)

    return decoder.inverse_transform(q_pred)


@app.route('/genres', methods=["POST"])
def predict():
    genres = predict_genres(request.get_json().get('description'))
    return {'genres': genres[0]}, 200


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=4321)

"""
url:
    http://localhost:4321/genres

body:
    {
    "description": "A car game, car simulator, driving game or racing game is a video game where the player controls a vehicle in a real-world or fantasy environment."
    }
"""