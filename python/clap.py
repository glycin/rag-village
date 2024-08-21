from msclap import CLAP

# Load model (Choose between versions '2022' or '2023')
# The model weight will be downloaded automatically if `model_fp` is not specified
clap_model = CLAP(version = '2023', use_cuda=True)

testText = ["An heavy metal song."]
filepath = ['C:\\Projects\\rag-village-data\\audio\\simple-loopable-beat.wav']

# Extract text embeddings
text_embeddings = clap_model.get_text_embeddings(testText)
print(text_embeddings)
# Extract audio embeddings
audio_embeddings = clap_model.get_audio_embeddings(filepath)
print(audio_embeddings)
# Compute similarity between audio and text embeddings 
similarities = clap_model.compute_similarity(audio_embeddings, text_embeddings)

print(f"{similarities}")
print("Trying out captioning")
caption_model = CLAP(version = 'clapcap', use_cuda=True)

audio_files = ['C:\\Projects\\rag-village-data\\audio\\simple-loopable-beat.wav',
               'C:\\Projects\\rag-village-data\\audio\\wingrandpiano.wav',
               'C:\\Projects\\rag-village-data\\audio\\Sad-Violin-A.wav',
               'C:\\Projects\\rag-village-data\\audio\\metal.00004.wav']

captions = caption_model.generate_caption(audio_files, resample=True, beam_size=5, entry_length=67, temperature=0.7)

for i in range(len(audio_files)):
    print(f"Audio file: {audio_files[i]} \n")
    print(f"Generated caption: {captions[i]} \n")