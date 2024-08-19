FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/smart-contact-manager-0.0.1-SNAPSHOT.jar smart-contact-manager.jar
EXPOSE 8090
ENV DB_URL=jdbc:mysql://localhost:3306/scm_db
ENV DB_USERNAME=root
ENV DB_PASSWORD=gotunamdev
ENV GOOGLE_CLIENT_ID=139941448428-gbk6otoe0k5pf48v91b5sr3t6t74434m.apps.googleusercontent.com
ENV GOOGLE_CLIENT_SECRET=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
ENV GITHUB_CLIENT_ID=Iv23lioKQ3v7zpjbdCDD
ENV GITHUB_CLIENT_SECRET=3282eae45dfb7454fd5c6610b2a217841ec0b8c2
ENV CLOUDINARY_CLOUD_NAME=dogkjseed
ENV CLOUDINARY_API_KEY=752257441416641
ENV CLOUDINARY_API_SECRET=y_nGBqybpm-UmyCI_wuC5f8dFG8
ENV MAIL_USERNAME=testing.goutam@gmail.com
ENV MAIL_PASSWORD=wdmpbsxtnwplumql
ENTRYPOINT ["java","-jar","smart-contact-manager.jar"]
