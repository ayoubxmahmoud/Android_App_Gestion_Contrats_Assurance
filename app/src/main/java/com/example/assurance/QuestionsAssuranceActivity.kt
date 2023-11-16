package com.example.assurance

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class QuestionsAssuranceActivity : AppCompatActivity() {
    private var questionReponsesList: MutableList<QuestionReponseAssurance>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions_assurance)

        // Initialiser la liste des questions et réponses
        questionReponsesList = ArrayList()
        questionReponsesList!!.add(QuestionReponseAssurance("Qu’est-ce qu’une assurance ?", "Une assurance est un contrat par lequel une entreprise (appelée assureur) s'engage, moyennant le versement d'une prime par l'assuré, à garantir celui-ci contre certains risques. En cas de réalisation de ces risques, l'assureur s'engage à indemniser l'assuré ou à lui fournir une prestation en nature, conformément aux termes du contrat. Les assurances peuvent couvrir un large éventail de risques, tels que les dommages matériels, les accidents, les maladies, le décès, les pertes financières, etc."))
        questionReponsesList!!.add(QuestionReponseAssurance("Pourquoi s’assurer ?", "L’assurance est un moyen de réduire la vulnérabilité des individus face aux aléas de la vie. Elle sert à se couvrir soi-même, couvrir ses biens, ainsi que les dommages engageant sa responsabilité.\n" + "\n"))
        questionReponsesList!!.add(QuestionReponseAssurance("Auprès de qui souscrire un contrat d’assurance ?\n","Il est possible de souscrire un contrat d'assurance auprès de différentes entités, selon le type d'assurance souhaité. Voici quelques exemples :\n" +
                "\n" +
                "Assurance automobile : compagnies d'assurance, courtiers d'assurance, banques, concessionnaires automobiles\n" +
                "Assurance habitation : compagnies d'assurance, courtiers d'assurance, banques, agences immobilières\n" +
                "Assurance santé : compagnies d'assurance, mutuelles, courtiers d'assurance\n" +
                "Assurance vie : compagnies d'assurance, banques, courtiers d'assurance, conseillers en gestion de patrimoine\n" +
                "Assurance voyage : compagnies d'assurance, agences de voyages, compagnies aériennes, courtiers d'assurance"))
        questionReponsesList!!.add(QuestionReponseAssurance("Dans quels cas je peux résilier mon contrat d’assurance ?","Le contrat prend fin à la date d’échéance prévue, mais dans certains cas, il peut être résilié prématurément :\n" +
                "\n" +
                "1 - La résiliation à l’échéance\n" +
                "Lorsque votre contrat d’assurance arrive à terme, il est possible que celui-ci soit automatiquement renouvelé (tacite reconduction). Pour mettre fin à la reconduction d’un tel contrat, vous devez le dénoncer moyennant le préavis de résiliation prévu au contrat.\n" +
                "Lorsque la durée du contrat est supérieure à une année, vous avez le droit de vous  retirer à l'expiration d'un an à compter de la date d’effet du contrat sous réserve d’en informer l’assureur avec un préavis au moins égal au minimum fixé par le contrat. Le délai de préavis est dans tous les cas compris entre 30 et 90 jours.\n" +
                "Lorsque la durée du contrat est supérieure à un an, elle doit être rappelée en caractères très apparents par une mention figurant au-dessus de votre signature. A défaut de cette mention, vous pouvez résilier le contrat chaque année à la date anniversaire de sa prise d’effet moyennant un préavis de 30 jours."))
        questionReponsesList!!.add(QuestionReponseAssurance("Quelle est la date d'effet du contrat?\n","La date d'effet est la date de prise d'effet des garanties. Elle peut être différente de la date de signature du contrat.\n" +
                "\n" +
                "Important : L'assurance de responsabilité civile automobile étant obligatoire, il est indispensable de vérifier la date d'effet du contrat pour éviter les périodes sans assurance"))
        // Ajoutez d'autres questions et réponses ici

        // Afficher les questions et réponses dans la vue
        val linearLayout = findViewById<LinearLayout>(R.id.linearLayout)
        for (qr in questionReponsesList as ArrayList<QuestionReponseAssurance>) {
            // Create a parent view for the question and answer pair
            val questionAnswerView = LinearLayout(this)
            questionAnswerView.orientation = LinearLayout.VERTICAL

            // Add padding to the question and answer views
            val padding = resources.getDimensionPixelSize(R.dimen.padding)
            questionAnswerView.setPadding(padding, padding, padding, padding)

            // Create and add the question TextView to the parent view
            val questionTextView = TextView(this)
            questionTextView.text = qr.question
            questionAnswerView.addView(questionTextView)

            // Create and add the answer TextView to the parent view
            val answerTextView = TextView(this)
            answerTextView.text = qr.reponse
            questionAnswerView.addView(answerTextView)

            // Add the parent view to the linear layout
            linearLayout.addView(questionAnswerView)
        }
    }
}




