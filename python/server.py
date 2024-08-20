from fastapi import FastAPI
from msclap import CLAP

app = FastAPI()
clap_model = CLAP(version = '2023', use_cuda=True)

@app.get("/embedding/audio/{filePath}")
async def embed_audio(filePath: str):
    print(f"Embedding audio at {filePath} ")
    file_path = [f'{filePath}']
    audio_embeddings = clap_model.get_audio_embeddings(file_path)
    print(f"Embedding done with {len(audio_embeddings.tolist()[0])} dimensions")
    return {"embedding": audio_embeddings.tolist()[0]}

@app.get("/embedding/text/{text}")
async def embed_text(text: str):
    print(f"Embedding text: {text}")
    text_input = [f'{text}']
    text_embeddings = clap_model.get_text_embeddings(text_input)
    print(f"Embedding done with {len(text_embeddings.tolist()[0])} dimensions")
    return {"embedding": text_embeddings.tolist()[0]}
