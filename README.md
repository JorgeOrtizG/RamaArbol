# RamaArbol
Programa que permite buscar la rama de un nodo , dado su id y tambien les establece niveles de profundidad
el programa hace una sola consulta en la base de datos para obtener información de todos los nodos incluyendo las 
reglas de formación del árbol ,despues  combina los métodos de busqueda de anchura y profundidad para buscar los 
padres del id dado y guardando esto en una lista , de esta manera partiendo del hecho de que el último nodo padre 
es el nodo padre de todos podemos el programa puede establecer el nivel de profundidad del nodo dado ,despues combina de  nuevo los metodos de busqueda de anchura y profundidad
 para obtener los nodos hijos y guardarlos en otra lista , al finalizar el programa devuelve un string en formato json 
 con ambos listados de nodos padres y nodos hijos
