package com.example.data

import com.example.model.*

object SampleData {

  val announcements = listOf(
    Announcement(
      id = "ann-1",
      title = "Cronograma de Mesas de Examen Previas y Libres - Octubre",
      date = "22 Septiembre 2026",
      category = "Exámenes",
      summary = "Se publicaron las fechas y comisiones evaluadoras para rendir materias previas. Inscripción abierta en Secretaría.",
      fullDetails = "Las mesas examinadoras tendrán lugar del 13 al 17 de octubre en ambos turnos. Recordá solicitar el programa de la materia en fotocopiadora o en la Biblioteca Digital de la app. Es requisito presentarse con DNI y uniforme reglamentario.",
      location = "Secretaría y Aulas 4 a 8",
      organizer = "Vicedirección Académica",
      isImportant = true
    ),
    Announcement(
      id = "ann-2",
      title = "Bufet Solidario del Centro de Estudiantes: Colecta de Invierno",
      date = "25 Septiembre 2026",
      category = "Solidaridad",
      summary = "Venta de meriendas caseras en el recreo largo. Lo recaudado se destinará a comedores comunitarios del barrio.",
      fullDetails = "El Centro de Estudiantes invita a participar del Bufet Solidario. Habrá alfajores artesanales, tortas y opciones sin TACC. También recibiremos donaciones de frazadas y ropa de abrigo en buen estado en la mesa de entrada.",
      location = "Patio Central - Durante los recreos",
      organizer = "Centro de Estudiantes (C.E.)",
      isImportant = false
    ),
    Announcement(
      id = "ann-3",
      title = "Feria Anual de Ciencias y Proyectos Tecnológicos 2026",
      date = "28 Septiembre 2026",
      category = "Eventos",
      summary = "Exposición de trabajos prácticos integradores de 1° a 5° año. Muestras interactivas y robótica.",
      fullDetails = "Invitamos a toda la comunidad educativa y familias a recorrer los stands preparados por los cursos de ciencias naturales y tecnología. Habrá experimentos en vivo, paneles de debate y presentaciones de podcasts estudiantiles.",
      location = "Gimnasio y Laboratorio Central",
      organizer = "Departamento de Ciencias Exactas y Naturales",
      isImportant = true
    ),
    Announcement(
      id = "ann-4",
      title = "Taller de Convivencia Digital: Prevención del Ciberacoso y Huella Digital",
      date = "30 Septiembre 2026",
      category = "Espacio Seguro",
      summary = "Jornada formativa para 3°, 4° y 5° año dictada por especialistas del Gabinete de Orientación.",
      fullDetails = "Un espacio de diálogo y reflexión sobre el uso responsable de redes sociales, protección de la intimidad, sexting no consentido y protocolos ante situaciones de ciberhostigamiento entre pares.",
      location = "Salón de Actos",
      organizer = "Gabinete de Orientación Escolar",
      isImportant = false
    )
  )

  val protocolSteps = listOf(
    ProtocolStep(
      stepNumber = 1,
      title = "Detección Temprana y Escucha Asertiva",
      subtitle = "Identificar señales de alerta sin minimizar el malestar",
      description = "El acoso o ciberacoso no es 'cosa de chicos' ni un juego de bromas si una persona se siente humillada, aislada o angustiada. Creer en el relato del estudiante es el punto de partida fundamental.",
      keyActions = listOf(
        "Escuchar con empatía y sin emitir juicios de valor.",
        "Garantizar que quien pide ayuda no será expuesto ante el grupo.",
        "Diferenciar un conflicto puntual de una conducta sistemática y asimétrica de hostigamiento."
      ),
      warningNote = "Nunca forzar una mediación cara a cara entre la víctima y quien hostiga mientras persista la situación de vulnerabilidad."
    ),
    ProtocolStep(
      stepNumber = 2,
      title = "Resguardo y Documentación Segura",
      subtitle = "Preservar evidencia digital o testimonios",
      description = "En casos de ciberacoso (grupos de WhatsApp, stickers denigratorios, perfiles falsos), es clave recopilar las pruebas de manera ordenada sin continuar la viralización.",
      keyActions = listOf(
        "Capturar pantallas completas donde se vean fechas, nombres de usuario o números.",
        "No responder ni alimentar agresiones o descalificaciones virtuales.",
        "Configurar la privacidad y bloquear cuentas agresoras tras tomar capturas."
      )
    ),
    ProtocolStep(
      stepNumber = 3,
      title = "Activación del Gabinete de Orientación",
      subtitle = "Comunicación inmediata al equipo escolar capacitado",
      description = "Cualquier estudiante, preceptor o docente que detecte un hecho debe canalizarlo mediante el botón de Reporte Confidencial de esta app o acercándose personalmente al Gabinete.",
      keyActions = listOf(
        "Envío de reporte confidencial (puede ser anónimo si el alumno lo prefiere).",
        "Recepción por parte de la Licenciada en Psicopedagogía o Trabajadora Social de la escuela.",
        "Contacto prioritario y discreto con el alumno afectado en un entorno seguro."
      )
    ),
    ProtocolStep(
      stepNumber = 4,
      title = "Intervención Pedagógica y Acompañamiento Continuo",
      subtitle = "Restauración de acuerdos y no repetición",
      description = "La escuela no busca la mera sanción punitiva, sino la responsabilización, el cese inmediato de las conductas violentas y el cuidado de la salud mental de todos los involucrados.",
      keyActions = listOf(
        "Citación respetuosa a las familias para acordar pautas de cuidado mutuo.",
        "Talleres grupales guiados para reconstruir los lazos comunitarios en el curso.",
        "Seguimiento periódico semanal durante todo el ciclo lectivo."
      )
    )
  )

  val esiTopics = listOf(
    EsiTopic(
      id = "esi-1",
      title = "Afectividad y Vínculos Saludables",
      subtitle = "Amor propio, empatía y relaciones libres de manipulación",
      category = "Afectividad",
      summary = "Reflexionar sobre cómo nos relacionamos con amigos y parejas. Identificar mitos del amor romántico que justifican el control, los celos y la invasión de la privacidad.",
      keyPoints = listOf(
        "Los celos y el control de contraseñas no son muestras de amor, sino vulneración de la intimidad.",
        "La comunicación asertiva permite expresar desacuerdos sin recurrir a la violencia o el ninguneo.",
        "Derecho a terminar un vínculo en cualquier momento sin sufrir presiones ni extorsiones."
      ),
      legalReference = "Eje 'Valorar la afectividad' - Lineamientos Curriculares Ley 26.150"
    ),
    EsiTopic(
      id = "esi-2",
      title = "Cuidado del Cuerpo y Consentimiento Explícito",
      subtitle = "Mi cuerpo me pertenece: límites personales y respeto",
      category = "Cuidado del Cuerpo",
      summary = "Comprender la autonomía corporal y que cualquier interacción física, emocional o sexual requiere consentimiento mutuo, lúcido, entusiasta y permanente.",
      keyPoints = listOf(
        "El silencio, la duda o la ausencia de un 'no' jamás equivalen a un 'sí'.",
        "El consentimiento puede retirarse en cualquier instante, sin importar antecedentes.",
        "Información científica y laica sobre salud sexual, métodos de barrera y anticoncepción gratuita en centros de salud."
      ),
      legalReference = "Ley Nacional 25.673 de Salud Sexual y Procreación Responsable"
    ),
    EsiTopic(
      id = "esi-3",
      title = "Derechos, Identidad y Convivencia Escolar",
      subtitle = "Diversidad, no discriminación e inclusión en el aula",
      category = "Derechos y Diversidad",
      summary = "Garantizar que la escuela secundaria sea un espacio libre de discriminación por orientación sexual, identidad de género, procedencia o características físicas.",
      keyPoints = listOf(
        "Derecho a ser llamado por el nombre de identidad autopercibida en listas y boletines.",
        "Rechazo activo al acoso escolar lgtbifóbico y a los estereotipos rígidos de género.",
        "Construcción de un ámbito de respeto mutuo donde todas las voces tengan valor."
      ),
      legalReference = "Ley de Identidad de Género N° 26.743 y Ley de Educación Nacional N° 26.206"
    ),
    EsiTopic(
      id = "esi-4",
      title = "Intimidad Digital y Prevención de Violencias",
      subtitle = "Protección de datos personales, sexting seguro y no difusión",
      category = "Vínculos Digitales",
      summary = "Pautas para el uso seguro de entornos digitales. Difundir o compartir fotos o videos íntimos de otra persona sin su consentimiento constituye una gravísima vulneración penada por la ley.",
      keyPoints = listOf(
        "Nunca reenviar contenidos íntimos ajenos; cortar las cadenas frena el daño.",
        "Reconocer el 'grooming' (adultos haciéndose pasar por adolescentes en internet).",
        "Recurrir a un adulto de confianza o al Gabinete ante cualquier intento de chantaje o extorsión."
      ),
      legalReference = "Ley 26.904 (Penalización del Grooming) y Ley 25.326 de Protección de Datos"
    )
  )

  val safeSpaceFaqs = listOf(
    SafeSpaceFaq(
      id = "sf-1",
      question = "¿Cómo funciona el Reporte Confidencial al Gabinete?",
      answer = "Al enviar un reporte desde la app, la información llega de forma cifrada y directa únicamente a las profesionales del Gabinete de Orientación Escolar. Si elegís la opción anónima, no guardamos tu identidad. El equipo analiza el caso con total discreción y diseña una estrategia de cuidado.",
      tags = listOf("#Acoso", "#Derechos", "#SaludMental")
    ),
    SafeSpaceFaq(
      id = "sf-2",
      question = "Veo que acosan a un compañero en el curso pero tengo miedo de involucrarme, ¿qué puedo hacer?",
      answer = "Testificar no significa pelear. Podés acercarte a la persona afectada en privado para hacerle saber que no está sola, no sumarte a risas o viralizaciones, y realizar un reporte anónimo en la app alertando la situación a los orientadores para que intervengan sin señalarte.",
      tags = listOf("#Acoso", "#Convivencia")
    ),
    SafeSpaceFaq(
      id = "sf-3",
      question = "¿Qué profesionales integran el Gabinete de Orientación de la escuela?",
      answer = "Está compuesto por la Lic. Laura Rossi (Psicopedagoga Institucional) y la Lic. Natalia Méndez (Trabajadora Social). Su función no es juzgar ni castigar, sino brindar escucha confidencial, orientación académica y acompañamiento en situaciones complejas.",
      tags = listOf("#SaludMental", "#Derechos")
    ),
    SafeSpaceFaq(
      id = "sf-4",
      question = "¿Qué canales telefónicos de ayuda existen fuera del horario escolar?",
      answer = "Podés comunicarte las 24 horas y de forma gratuita con la Línea 102 (Atención especializada sobre los derechos de niñas, niños y adolescentes), la Línea 144 (Violencia de género y orientación) y la Línea 135 (Centro de Asistencia al Suicida).",
      tags = listOf("#Derechos", "#SaludMental")
    ),
    SafeSpaceFaq(
      id = "sf-5",
      question = "¿Puedo solicitar talleres de ESI sobre un tema específico para mi curso?",
      answer = "¡Sí! El Centro de Estudiantes y los delegados de cada curso pueden proponer temáticas (salud mental, vínculos, consentimiento, diversidad) acercándose al Gabinete o a las profesoras coordinadoras del Proyecto Institucional de ESI.",
      tags = listOf("#ESI", "#Derechos", "#Convivencia")
    )
  )

  val initialResources = listOf(
    StudyResource(
      id = "res-1",
      title = "Resumen Completo: Funciones Cuadráticas y Polinómicas",
      subject = "Matemática",
      gradeYear = "4° Año",
      resourceType = "Resumen",
      description = "Incluye fórmulas de vértice, eje de simetría, raíces por fórmula de Baskara y 15 ejercicios resueltos paso a paso con gráficos explicativos.",
      contributor = "Valentín S. (4° B)",
      uploadDate = "15 Sep 2026",
      fileType = "PDF",
      fileSize = "3.2 MB",
      rating = 4.9f,
      ratingCount = 28,
      isFavorite = true
    ),
    StudyResource(
      id = "res-2",
      title = "Modelo de Examen y Cuadro Sinóptico: Guerra Fría y Bloques",
      subject = "Historia",
      gradeYear = "4° Año",
      resourceType = "Modelo de Examen",
      description = "Modelo de parcial con respuestas modelo sobre Plan Marshall, Doctrina Truman, Muro de Berlín y crisis de los misiles en Cuba.",
      contributor = "Julieta R. (4° A)",
      uploadDate = "18 Sep 2026",
      fileType = "PDF",
      fileSize = "2.1 MB",
      rating = 4.8f,
      ratingCount = 34,
      isFavorite = false
    ),
    StudyResource(
      id = "res-3",
      title = "Guía de Lectura: Mito de la Caverna y Teoría de las Ideas",
      subject = "Filosofía",
      gradeYear = "5° Año",
      resourceType = "Guía",
      description = "Análisis conceptual de República VII de Platón, cuadro comparativo Mundo Sensible vs Mundo Inteligible, y glosario de términos griegos.",
      contributor = "Mateo G. (5° C)",
      uploadDate = "10 Sep 2026",
      fileType = "DOCX",
      fileSize = "1.4 MB",
      rating = 4.7f,
      ratingCount = 19,
      isFavorite = false
    ),
    StudyResource(
      id = "res-4",
      title = "Apuntes de Clase: Genética Mendeliana y Árboles Genealógicos",
      subject = "Biología",
      gradeYear = "2° Año",
      resourceType = "Apuntes",
      description = "Leyes de Mendel explicadas con cuadros de Punnett ilustrados. Cruzamientos monohíbridos y dihíbridos con ejemplos cotidianos.",
      contributor = "Agustina P. (2° A)",
      uploadDate = "05 Sep 2026",
      fileType = "PDF",
      fileSize = "4.0 MB",
      rating = 4.6f,
      ratingCount = 22,
      isFavorite = true
    ),
    StudyResource(
      id = "res-5",
      title = "Cheat Sheet: Verb Tenses, Conditionals & Passive Voice",
      subject = "Inglés",
      gradeYear = "3° Año",
      resourceType = "Resumen",
      description = "Tabla sintética con todos los tiempos verbales (Present Perfect, Past Continuous, etc.) y reglas para cambiar de voz activa a pasiva.",
      contributor = "Ignacio T. (3° B)",
      uploadDate = "12 Sep 2026",
      fileType = "PDF",
      fileSize = "1.8 MB",
      rating = 4.9f,
      ratingCount = 41,
      isFavorite = false
    ),
    StudyResource(
      id = "res-6",
      title = "Resumen y Análisis de 'Martín Fierro' y Literatura Gauchesca",
      subject = "Lengua y Literatura",
      gradeYear = "5° Año",
      resourceType = "Resumen",
      description = "Análisis canto por canto de La Ida y La Vuelta. Temas clave: la frontera, la injusticia social y la voz del gaucho cantor.",
      contributor = "Clara M. (5° B)",
      uploadDate = "02 Sep 2026",
      fileType = "DOCX",
      fileSize = "2.8 MB",
      rating = 4.8f,
      ratingCount = 17,
      isFavorite = false
    ),
    StudyResource(
      id = "res-7",
      title = "Problemas Resueltos: Cinemática MRU y MRUV",
      subject = "Física",
      gradeYear = "3° Año",
      resourceType = "Guía",
      description = "Despejes de fórmulas de aceleración, velocidad final, distancia y gráficos de posición en función del tiempo explicados minuciosamente.",
      contributor = "Lucas D. (3° C)",
      uploadDate = "20 Ago 2026",
      fileType = "PDF",
      fileSize = "3.5 MB",
      rating = 4.7f,
      ratingCount = 30,
      isFavorite = false
    )
  )

  val teachers = listOf(
    Teacher(
      id = "t-1",
      name = "Prof. Mariana Gómez",
      title = "Licenciada en Matemática y Profesora de Enseñanza Secundaria",
      subjects = listOf("Matemática 3° A", "Matemática 4° B"),
      courses = "3° Año A (Turno Mañana) y 4° Año B (Turno Tarde)",
      email = "mariana.gomez@aulaconectada.edu.ar",
      consultationHours = "Martes de 14:30 a 16:00 hs y Jueves de 10:15 a 11:30 hs",
      classroom = "Aula 12 (Pabellón Central)",
      evaluationCriteria = listOf(
        "2 Evaluaciones escritas individuales por trimestre (promediables).",
        "Trabajos Prácticos grupales con defensa oral (se evalúa proceso y fundamentación).",
        "Carpeta completa y participación activa en clase (10% de la nota conceptual).",
        "Se aprueba con nota mínima de 6 (seis) sin decimales en el cierre trimestral."
      ),
      assignmentRules = listOf(
        "Entregas en formato digital únicamente vía PDF en la plataforma escolar o impreso prolijo en clase.",
        "Carátula obligatoria: Materia, curso, apellido y nombre de los integrantes y fecha de entrega.",
        "Los ejercicios deben incluir todo el desarrollo algebraico manuscrito; no se convalidan resultados aislados.",
        "Trabajos entregados fuera de término sufren un descuento de 1 punto por día hábil de demora."
      ),
      recentUpdate = "Guía Práctica N° 4 subida a la plataforma. La fecha límite de entrega del TP sobre Parábolas se extendió al viernes 3 de octubre."
    ),
    Teacher(
      id = "t-2",
      name = "Prof. Carlos Benítez",
      title = "Profesor en Historia y Ciencias Sociales",
      subjects = listOf("Historia Argentina y Mundial 4° B", "Geografía 2° A"),
      courses = "4° Año División B y 2° Año División A",
      email = "carlos.benitez@aulaconectada.edu.ar",
      consultationHours = "Lunes de 11:30 a 12:45 hs y Miércoles de 15:00 a 16:15 hs",
      classroom = "Sala de Profesores / Aula 8",
      evaluationCriteria = listOf(
        "Análisis crítico de fuentes históricas primarias y secundarias.",
        "Uso correcto de vocabulario disciplinar y contextualización temporal y espacial.",
        "Participación fundamentada en debates áulicos y respeto por las intervenciones ajenas.",
        "Nota mínima de aprobación: 6 (seis)."
      ),
      assignmentRules = listOf(
        "Citas bibliográficas según normas APA simplificadas al final del trabajo.",
        "Prohibido el copiado textual sin citar fuentes o uso desleal de herramientas de IA sin reflexión propia.",
        "Entrega en hoja A4, tipografía legible o manuscrito con tinta azul/negra."
      ),
      recentUpdate = "El debate sobre las consecuencias socioeconómicas del período de entreguerras se realizará la próxima clase. Repasar capítulos 5 y 6."
    ),
    Teacher(
      id = "t-3",
      name = "Prof. Lucía Ferraro",
      title = "Licenciada en Letras Modernas",
      subjects = listOf("Lengua y Literatura 4° B", "Literatura 5° A"),
      courses = "4° Año División B y 5° Año División A",
      email = "lucia.ferraro@aulaconectada.edu.ar",
      consultationHours = "Miércoles de 09:30 a 10:45 hs y Viernes de 13:30 a 14:30 hs",
      classroom = "Biblioteca Escolar (Sector Silencioso)",
      evaluationCriteria = listOf(
        "Comprensión lectora, coherencia y cohesión en la producción textual.",
        "Ortografía, acentuación y puntuación adecuada (se descuenta puntaje por errores reiterados).",
        "Lectura obligatoria de las 3 novelas trimestrales y control de lectura individual."
      ),
      assignmentRules = listOf(
        "Los ensayos deben incluir título original y epígrafe introductorio.",
        "Extensión mínima: 2 carillas; extensión máxima: 4 carillas.",
        "Las entregas se realizan puntualmente al inicio de la hora de clase pautada."
      ),
      recentUpdate = "Disponibles en fotocopiadora los cuadernillos de microrrelatos latinoamericanos para el proyecto intercolegial."
    ),
    Teacher(
      id = "t-4",
      name = "Prof. Esteban Morales",
      title = "Licenciado en Filosofía y Ciencias de la Educación",
      subjects = listOf("Filosofía 5° A y B", "Construcción de Ciudadanía 3° C"),
      courses = "5° Año y 3° Año Turno Mañana",
      email = "esteban.morales@aulaconectada.edu.ar",
      consultationHours = "Viernes de 10:00 a 12:00 hs",
      classroom = "Aula Magna",
      evaluationCriteria = listOf(
        "Capacidad de argumentación dialéctica y problematización de conceptos.",
        "Rigor en la lectura de textos filosóficos y elaboración de bitácoras de pensamiento.",
        "Asistencia a coloquios de fin de ciclo con 80% de regularidad requerida."
      ),
      assignmentRules = listOf(
        "Ensayos libres con defensa oral obligatoria en pequeños grupos.",
        "Valoración especial a la formulación de preguntas filosóficas originales antes que a respuestas mecánicas."
      ),
      recentUpdate = "Publicado el cronograma para las exposiciones orales sobre el dilema ético contemporáneo de la inteligencia artificial."
    ),
    Teacher(
      id = "t-5",
      name = "Prof. Sofía Valenzuela",
      title = "Traductora Pública y Profesora en Lengua Inglesa",
      subjects = listOf("Inglés General 3° B", "Inglés 4° B"),
      courses = "3° Año B y 4° Año B",
      email = "sofia.valenzuela@aulaconectada.edu.ar",
      consultationHours = "Jueves de 08:00 a 09:30 hs",
      classroom = "Laboratorio de Idiomas",
      evaluationCriteria = listOf(
        "Evaluación de las 4 macrohabilidades: Reading, Writing, Listening & Speaking.",
        "Ejercicios prácticos semanales en plataforma interactiva.",
        "Presentación de un proyecto audiovisual en inglés al cierre de cada semestre."
      ),
      assignmentRules = listOf(
        "Uso exclusivo del idioma inglés durante la presentación oral.",
        "Corrección colaborativa de borradores de redacción antes de la entrega final."
      ),
      recentUpdate = "El test de listening de la Unidad 3 se tomará este jueves a primera hora. Recordar traer auriculares propios para el laboratorio."
    )
  )

  val schoolFaqs = listOf(
    SchoolFaqItem(
      id = "faq-1",
      category = "Reglamento y Convivencia",
      question = "¿Cuál es el protocolo sobre el uso de teléfonos celulares en el aula?",
      answer = "El uso del teléfono móvil en horas de clase está permitido exclusivamente con fines pedagógicos explicitados previamente por el docente a cargo. Durante explicaciones y exámenes, los dispositivos deben permanecer guardados o en el fichero del aula en modo silencioso para favorecer la concentración."
    ),
    SchoolFaqItem(
      id = "faq-2",
      category = "Asistencias y Faltas",
      question = "¿Cuál es el límite máximo de inasistencias y cómo justificarlas?",
      answer = "El régimen escolar establece un tope de 20 inasistencias anuales (institucionales y médicas). Para justificar una falta por razones de salud, el estudiante o tutor debe presentar el certificado médico oficial en Preceptoría dentro de las 48 horas hábiles del reintegro."
    ),
    SchoolFaqItem(
      id = "faq-3",
      category = "Asistencias y Faltas",
      question = "¿Qué sucede si un estudiante supera las 20 inasistencias?",
      answer = "Al alcanzar las 20 faltas, el tutor legal debe solicitar formalmente la 1° Reincorporación ante Dirección, comprometiendo al alumno a mantener regularidad estricta (hasta un máximo de 5 faltas adicionales otorgadas por Consejo de Convivencia previa evaluación del caso)."
    ),
    SchoolFaqItem(
      id = "faq-4",
      category = "Certificados y Trámites",
      question = "¿Cómo solicito una Constancia de Alumno Regular o de Examen?",
      answer = "La Constancia de Alumno Regular puede solicitarse de manera presencial en Secretaría o descargarse firmada digitalmente a través de la ventanilla virtual institucional. La entrega física demora 24 a 48 hs hábiles."
    ),
    SchoolFaqItem(
      id = "faq-5",
      category = "Certificados y Trámites",
      question = "¿Cómo tramitar el Boleto Especial Educativo / Estudiantil?",
      answer = "Debés estar matriculado regularmente. Presentar en Preceptoría fotocopia de DNI, constancia de inscripción y formulario de la empresa de transporte firmado por el tutor. La escuela sella el formulario para su validación en las terminales SUBE."
    ),
    SchoolFaqItem(
      id = "faq-6",
      category = "Reglamento y Convivencia",
      question = "¿Cuáles son las pautas de vestimenta y uniforme escolar?",
      answer = "Se promueve el uso de vestimenta cómoda, segura y respetuosa de la actividad pedagógica (chomba o remera institucional, pantalón deportivo o jean sin roturas excesivas, calzado cerrado para clases de educación física y laboratorios)."
    )
  )

  val institutionalContacts = listOf(
    InstitutionalContact(
      id = "c-1",
      department = "Rectoría y Equipo Directivo",
      role = "Dirección Institucional",
      personInCharge = "Prof. Lic. Marcelo Argañaraz",
      phone = "(011) 4582-9011",
      email = "rectoria@aulaconectada.edu.ar",
      office = "Planta Baja - Ala Este",
      schedule = "Lunes a Viernes de 08:00 a 17:00 hs"
    ),
    InstitutionalContact(
      id = "c-2",
      department = "Secretaría de Alumnos",
      role = "Trámites, Títulos y Matrículas",
      personInCharge = "Sra. Andrea Quiroga",
      phone = "(011) 4582-9012 int. 104",
      email = "secretaria@aulaconectada.edu.ar",
      office = "Entrada Principal - Ventanilla 1",
      schedule = "Turno Mañana: 08:30 a 12:00 hs | Turno Tarde: 13:30 a 17:00 hs"
    ),
    InstitutionalContact(
      id = "c-3",
      department = "Gabinete de Orientación Escolar",
      role = "Acompañamiento Psicopedagógico y ESI",
      personInCharge = "Lic. Laura Rossi & Lic. Natalia Méndez",
      phone = "(011) 4582-9013",
      email = "gabinete.orientacion@aulaconectada.edu.ar",
      office = "1° Piso - Gabinete Silencioso (Junto a Biblioteca)",
      schedule = "Lunes a Viernes de 08:00 a 18:00 hs"
    ),
    InstitutionalContact(
      id = "c-4",
      department = "Preceptoría General",
      role = "Control de Asistencia y Libretas",
      personInCharge = "Preceptor Jefe: Gustavo Morales",
      phone = "(011) 4582-9014 int. 108",
      email = "preceptoria@aulaconectada.edu.ar",
      office = "Planta Baja - Galería Central",
      schedule = "Turno Mañana: 07:15 a 13:00 hs | Turno Tarde: 12:45 a 18:30 hs"
    ),
    InstitutionalContact(
      id = "c-5",
      department = "Centro de Estudiantes (C.E.)",
      role = "Representación Gremial Estudiantil",
      personInCharge = "Presidenta C.E.: Candela Bravo (5° A)",
      phone = "(011) 15-6892-3341",
      email = "centro.estudiantes@aulaconectada.edu.ar",
      office = "Subsuelo - Sala de Estudiantes",
      schedule = "Abierto durante todos los recreos y contraturnos"
    )
  )
}
