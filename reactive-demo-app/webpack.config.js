var ExtractTextPlugin = require('extract-text-webpack-plugin');
var path = require("path");

var config = {
    entry: ["./src/main/webapp/index.tsx"],
    output: {
        path: path.resolve(__dirname, "src/ratpack/public"),
        filename: "./scripts/bundle.js"
    },
    resolve: {
        extensions: [".ts", ".tsx", ".js"]
    },
    module: {
        loaders: [
            { // regular css files
                test: /\.css$/,
                //use: 'css-loader'
                use: ExtractTextPlugin.extract({
                    use: 'css-loader'
                })
            },

            { // sass / scss loader for webpack
                test: /\.scss$/,
                use: ExtractTextPlugin.extract(['css-loader', 'sass-loader'])
            },

            {
                test: /\.tsx?$/,
                loader: "ts-loader",
                exclude: /node_modules/
            }
        ]
    },
    plugins: [
        new ExtractTextPlugin({ // define where to save the file
            filename: "./styles/demo.css",
            allChunks: true
        })
    ]
};

module.exports = config;
