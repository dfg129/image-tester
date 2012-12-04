package test.image

import scala.concurrent.Future
import akka.actor.{ActorSystem, Props}
import spray.can.client.{HttpDialog, HttpClient}
import spray.http.{HttpResponse, HttpRequest}
import spray.io.IOExtension
import spray.util._


object SimpleExample extends App {
  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("simple-example")
  import system.log

  // every spray-can HttpClient (and HttpServer) needs an IOBridge for low-level network IO
  // (but several servers and/or clients can share one)
  val ioBridge = IOExtension(system).ioBridge

  // create and start the spray-can HttpClient
  val httpClient = system.actorOf(
    props = Props(new HttpClient(ioBridge)),
    name = "http-client"
  )

val queries = Seq(
    "//ig/PLD-WED-OV-50387WB/PO/1/SS-Q-Q/400/sterling-silver-ring-with-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-R-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-A-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-B-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-C-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-D-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-E-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-G-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-H-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-I-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-J-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-K-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-L-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-M-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-N-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-O-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-P-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-Q-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-S-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-T-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-U-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-V-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-X-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-Y-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-Z-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-7-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-8-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/SS-Q-Q/120/sterling-silver-ring-with-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-R-A/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-A-B/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-B-C/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-C-D/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-D-E/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-E-F/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-G-G/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-H-H/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-I-I/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-J-A/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-K-B/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-L-C/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-M-N/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-N-M/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-O-L/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-P-K/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-Q-J/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-S-I/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-T-H/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-U-G/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-V-F/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-X-E/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-Y-D/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-Z-C/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-7-B/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-8-A/120/sterling-silver-ring-with-emerald-aquamarine.jpg",

    "//ig/PLD-WED-OV-50387WB/PO/1/SS-Q-Q/400/sterling-silver-ring-with-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-R-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-A-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-B-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-C-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-D-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-E-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-G-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-H-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-I-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-J-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-K-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-L-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-M-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-N-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-O-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-P-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-Q-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-S-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-T-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-U-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-V-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-X-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-Y-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-Z-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-7-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-8-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/SS-Q-Q/400/sterling-silver-ring-with-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-R-A/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-A-B/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-B-C/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-C-D/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-D-E/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-E-F/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-G-G/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-H-H/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-I-I/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-J-A/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-K-B/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-L-C/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-M-N/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-N-M/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-O-L/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-P-K/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-Q-J/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-S-I/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-T-H/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-U-G/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-V-F/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-X-E/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-Y-D/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-Z-C/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-7-B/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-8-A/400/sterling-silver-ring-with-emerald-aquamarine.jpg",

     "//ig/PLD-WED-OV-50387WB/PO/1/SS-Q-Q/180/sterling-silver-ring-with-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-R-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-A-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-B-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-C-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-D-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-E-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-G-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-H-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-I-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-J-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-K-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-L-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-M-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-N-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-O-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-P-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-Q-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-S-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-T-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-U-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-V-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-X-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-Y-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-Z-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-7-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-8-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/SS-Q-Q/180/sterling-silver-ring-with-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-R-A/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-A-B/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-B-C/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-C-D/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-D-E/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-E-F/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-G-G/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-H-H/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-I-I/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-J-A/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-K-B/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-L-C/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-M-N/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-N-M/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-O-L/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-P-K/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-Q-J/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-S-I/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-T-H/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-U-G/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-V-F/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-X-E/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-Y-D/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-Z-C/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-7-B/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/PO/1/18WG-8-A/180/sterling-silver-ring-with-emerald-aquamarine.jpg",

     "//ig/PLD-WED-OV-50387WB/LD/1/SS-Q-Q/400/sterling-silver-ring-with-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-R-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-A-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-B-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-C-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-D-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-E-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-G-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-H-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-I-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-J-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-K-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-L-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-M-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-N-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-O-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-P-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-Q-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-S-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-T-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-U-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-V-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-X-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-Y-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-Z-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-7-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-8-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/SS-Q-Q/120/sterling-silver-ring-with-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-R-A/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-A-B/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-B-C/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-C-D/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-D-E/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-E-F/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-G-G/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-H-H/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-I-I/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-J-A/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-K-B/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-L-C/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-M-N/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-N-M/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-O-L/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-P-K/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-Q-J/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-S-I/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-T-H/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-U-G/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-V-F/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-X-E/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-Y-D/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-Z-C/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-7-B/120/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-8-A/120/sterling-silver-ring-with-emerald-aquamarine.jpg",

    "//ig/PLD-WED-OV-50387WB/LD/1/SS-Q-Q/400/sterling-silver-ring-with-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-R-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-A-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-B-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-C-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-D-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-E-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-G-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-H-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-I-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-J-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-K-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-L-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-M-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-N-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-O-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-P-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-Q-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-S-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-T-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-U-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-V-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-X-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-Y-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-Z-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-7-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-8-Q/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/SS-Q-Q/400/sterling-silver-ring-with-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-R-A/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-A-B/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-B-C/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-C-D/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-D-E/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-E-F/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-G-G/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-H-H/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-I-I/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-J-A/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-K-B/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-L-C/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-M-N/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-N-M/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-O-L/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-P-K/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-Q-J/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-S-I/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-T-H/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-U-G/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-V-F/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-X-E/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-Y-D/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-Z-C/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-7-B/400/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-8-A/400/sterling-silver-ring-with-emerald-aquamarine.jpg",

     "//ig/PLD-WED-OV-50387WB/LD/1/SS-Q-Q/180/sterling-silver-ring-with-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-R-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-A-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-B-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-C-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-D-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-E-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-G-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-H-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-I-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-J-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-K-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-L-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-M-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-N-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-O-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-P-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-Q-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-S-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-T-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-U-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-V-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-X-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-Y-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-Z-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-7-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-8-Q/180/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/SS-Q-Q/40/sterling-silver-ring-with-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-R-A/40/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-A-B/40/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-B-C/40/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-C-D/40/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-D-E/40/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-E-F/40/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-G-G/40/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-H-H/40/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-I-I/40/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-J-A/40/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-K-B/40/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-L-C/40/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-M-N/40/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-N-M/40/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-O-L/40/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-P-K/40/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-Q-J/40/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-S-I/40/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-T-H/40/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-U-G/40/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-V-F/40/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-X-E/40/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-Y-D/40/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-Z-C/40/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-7-B/40/sterling-silver-ring-with-emerald-aquamarine.jpg",
    "//ig/PLD-WED-OV-50387WB/LD/1/18WG-8-A/40/sterling-silver-ring-with-emerald-aquamarine.jpg"
) 

    

 val requests = queries.map(q => HttpRequest(uri = q))

  // create a very basic HttpDialog that results in a Future[HttpResponse]
  log.info("Dispatching GET request to github.com")
// val responseFuture =
 //  HttpDialog(httpClient, "mirror.gemvara.com")
  //   .send(HttpRequest(uri = "//ig/PLD-WED-OV-50387WB/PO/1/18WG-E-Q/120/sterling-silver-ring-with-emerald-aquamarine.jpg"))
  //    .end

    val responseFuture = timed(HttpDialog(httpClient, "mirror.gemvara.com").send(requests).end)
  responseFuture.onSuccess(secondRun)
  responseFuture.onFailure(printError andThen shutdown)

 def secondRun: PartialFunction[Any, Unit] = {
    case _ =>
   def httpDialog(r: HttpRequest) = HttpDialog(httpClient, "mirror.gemvara.com").send(r).end
   val responseFuture = timed(Future.sequence(requests.map(httpDialog)))
   responseFuture.onSuccess(printResult andThen shutdown)
   responseFuture.onFailure(printError andThen shutdown)
}

   def printResult: PartialFunction[(Seq[HttpResponse], Long), Unit] = {
    case (responses, time) =>
      log.info(responses.map(_.entity.buffer.length).mkString("Result bytes: ", ", ", "."))
      val rate = queries.size * 1000 / time
      log.info("Completed: {} requests in {} ms at a rate of  {} req/sec\n", queries.size, time, rate)
  }

  def printError: PartialFunction[Throwable, Unit] = {
    case e: Exception => log.error("Error: {}", e)
  }

  def shutdown: PartialFunction[Any, Unit] = {
    case _ =>
      system.shutdown()
  }

  def timed[T](block: => Future[T]) = {
    val startTime = System.currentTimeMillis
    block.map(_ -> (System.currentTimeMillis - startTime))
  }

  // "hook in" our continuation
  /*responseFuture onComplete {
    case Success(response) =>
      log.info(
        """|Result from host:
           |status : {}
           |headers: {}
           |body   : {}""".stripMargin,
        response.status, response.headers.mkString("\n  ", "\n  ", "")
      )
      system.shutdown()

    case Failure(error) =>
      log.error("Could not get response due to {}", error)
      system.shutdown()
  }*/


}
