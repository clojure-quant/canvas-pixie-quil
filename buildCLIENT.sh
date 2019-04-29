# figwheel has no extern config, therefore use cljsbuild
# lein clean
# lein fig:min

rm resources/public/cljs-out/dev-main.js
lein cljsbuild once min


