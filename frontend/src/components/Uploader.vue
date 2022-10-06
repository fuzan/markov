<template>
  <div>
    <el-row>
      <div class="title">
        <h1>Text Generator</h1>
      </div>
    </el-row>
    <el-row>
      <el-col :span="30">
        <el-form label-width="120px">
          <el-form-item>
            <div>
              Key size, decide how many words as prefix:
              <el-input-number v-model="keySize" @change="keySizeChange" :min="1" :max="6"></el-input-number>
            </div>
          </el-form-item>

          <el-form-item>
            <div>
              Word numbers, decide how many words you want to generate:
              <el-input-number v-model="numberOfWords" @change="keySizeChange" :min="20" :max="100"></el-input-number>
            </div>
          </el-form-item>

          <el-form-item>
            <label>
              Text based file, file size must be smaller than 512KB !
              <input
                type="file"
                ref="file"
                @change="selectFile"
                multiple="multiple"
              />
            </label>
            <button class="btn btn-success" :disabled="!selectedFiles" @click="upload">Upload</button>
          </el-form-item>
          <el-form-item>
            <el-button
              v-show="generate"
              type="primary"
              @click="generateText"
            >Generate Text and See magic non-sense!</el-button>
            <el-card v-if="generateDisplay">
              <div>{{generatedText}}</div>
            </el-card>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import axios from "axios";

export default {
  props: {
    msg: String
  },
  data() {
    return {
      selectedFiles: undefined,
      files: [],
      currentFile: false,
      keySize: 1,
      numberOfWords: 50,
      message: "",
      generate: false,
      generateDisplay: false,
      generatedText: "",
      fileName: ""
    };
  },
  methods: {
    selectFile() {
      this.selectedFiles = this.$refs.file.files;
      this.fileName = this.selectedFiles.item(0).name;
      console.log(this.fileName);
    },
    keySizeChange(val) {
      console.table(val);
    },
    generateText() {
      axios({
        method: "get",
        url:
          "http://localhost:9000/api/generate?keySize=" +
          this.keySize +
          "&numberOfWords=" +
          this.numberOfWords +
          "&fileName=" +
          this.fileName
      })
        .then(response => {
          this.generatedText = response.data;
          this.generateDisplay = true;
        })
        .catch(response => {
          this.alerting(response.response.data);
          this.currentFile = undefined;
          this.generate = false;
          this.generateDisplay = false;
        });
    },
    upload() {
      this.progress = 0;
      this.currentFile = this.selectedFiles.item(0);
      let fdata = new FormData();
      fdata.append("file", this.currentFile);
      axios({
        method: "post",
        url: "http://localhost:9000/api/upload",
        data: fdata,
        config: {
          "Content-type": "multipart/form-data"
        }
      })
        .then(response => {
          this.generate = true;
          this.generateDisplay = false;
          this.alertingSuccess("upload success");
        })
        .catch(response => {
          this.alerting(response.response.data);
          this.currentFile = undefined;
          this.generate = false;
          this.generateDisplay = false;
        });
      this.selectedFiles = undefined;
    },
    alerting(message) {
      this.$message.error(message);
    },
    alertingSuccess(message) {
      this.$message({ message: message, type: "success" });
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
div {
  text-align: left;
}
.title {
  text-align: center;
}
.box-card {
  width: 600px;
}
</style>
