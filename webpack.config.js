const path = require('path');
const VueLoaderPlugin = require('vue-loader/lib/plugin');

module.exports = {
    mode: 'development', // версия для разработки
    devtool: 'source-map',
    entry: path.join(__dirname, 'src', 'main', 'resources', 'static', 'js', 'main.js'), // отсюда webpack будет начинать сборку проекта
    devServer: {
        contentBase: './dist',
        compress: true,
        port: 8000,
        allowedHosts: [
            'localhost:9000'
        ]
    },
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /(node_modules|bower_components)/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env']
                    }
                }
            },
            {
                test: /\.vue$/,
                loader: 'vue-loader'
            }
        ]
    },
    plugins: [
        new VueLoaderPlugin()
    ],
    resolve: {
        modules: [
            path.join(__dirname, 'src', 'main', 'resources', 'static', 'js'),
            path.join(__dirname, 'node_modules'),
        ],
    }
}